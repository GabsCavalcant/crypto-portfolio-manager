// --- CONFIGURAÇÕES GLOBAIS ---
const API_URL = "http://localhost:8080/api";

// Tenta pegar a última carteira usada. Se não tiver, começa como null (vai forçar pegar a primeira da lista)
let WALLET_ID = localStorage.getItem("selectedWalletId"); 

document.addEventListener("DOMContentLoaded", async () => {
    
    // A. Carrega tudo imediatamente na primeira vez
    carregarTickerMercado();
    await carregarListaCarteiras();

    // B. Configura o "Relógio" para atualizar sozinho a cada 30 segundos
	setInterval(() => {
	    console.log("🔄 Buscando novos preços...");
	    carregarTickerMercado(); 
	    if (WALLET_ID) carregarCarteira(); 
	}, 60000); // 30000 ms = 30 segundos
});

// --- 2. FORMATADORES (Deixam o dinheiro bonito) ---
const formatarMoeda = (valor) => {
    if (valor === undefined || valor === null) return "R$ 0,00";
    return new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL'
    }).format(valor);
};

const formatarPorcentagem = (valor) => {
    if (!valor) return "0.00%";
    const sinal = valor >= 0 ? "+" : "";
    return `${sinal}${valor.toFixed(2)}%`;
};

// --- 3. BARRA DE COTAÇÕES (TICKER) ---
async function carregarTickerMercado() {
    const container = document.getElementById('market-ticker-bar');
    
    try {
        const response = await fetch(`${API_URL}/market/top`);
        if (!response.ok) throw new Error("Erro na API de Mercado");
        
        const dados = await response.json();
        
        container.innerHTML = ''; // Limpa o "Carregando..."

        dados.forEach(coin => {
            const card = document.createElement('div');
            card.className = 'market-card';
            card.innerHTML = `
                <div class="ticker-symbol">${coin.symbol.toUpperCase()}</div>
                <div class="ticker-price">${formatarMoeda(coin.currentPrice)}</div>
            `;
            container.appendChild(card);
        });

    } catch (error) {
        console.error("Erro ao carregar ticker:", error);
        container.innerHTML = '<div style="color:#aaa; font-size:0.8rem;">Mercado indisponível</div>';
    }
}

// --- 4. GERENCIAMENTO DE CARTEIRAS ---
async function carregarListaCarteiras() {
    try {
        const response = await fetch(`${API_URL}/wallets`);
        const carteiras = await response.json();
        
        const select = document.getElementById("wallet-select");
        select.innerHTML = ""; 

        if (carteiras.length > 0) {
            // Se não tinha carteira selecionada (primeiro acesso), pega a primeira
            if (!WALLET_ID) {
                WALLET_ID = carteiras[0].id;
                localStorage.setItem("selectedWalletId", WALLET_ID);
            }

            carteiras.forEach(w => {
                const option = document.createElement("option");
                option.value = w.id;
                option.innerText = w.name;
                if (w.id == WALLET_ID) option.selected = true;
                select.appendChild(option);
            });

            // Carrega os dados da carteira atual
            carregarCarteira(); 
        } else {
            // Caso não exista nenhuma carteira criada ainda
            select.innerHTML = "<option>Crie uma carteira</option>";
            document.getElementById("assets-grid").innerHTML = '<p class="no-data">Crie sua primeira carteira clicando no + acima.</p>';
        }

    } catch (error) {
        console.error("Erro ao listar carteiras:", error);
    }
}

function mudarCarteira() {
    const select = document.getElementById("wallet-select");
    WALLET_ID = select.value;
    localStorage.setItem("selectedWalletId", WALLET_ID);
    carregarCarteira();
}

async function salvarNovaCarteira() {
    const nomeInput = document.getElementById("wallet-name");
    const nome = nomeInput.value;
    
    if(!nome) return alert("Digite um nome para a carteira!");

    try {
        const response = await fetch(`${API_URL}/wallets`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name: nome })
        });

        if(response.ok) {
            const novaCarteira = await response.json();
            alert("Carteira criada com sucesso!");
            
            WALLET_ID = novaCarteira.id;
            localStorage.setItem("selectedWalletId", WALLET_ID);
            
            nomeInput.value = "";
            fecharModalCarteira();
            await carregarListaCarteiras(); // Recarrega tudo
        } else {
            alert("Erro ao criar carteira.");
        }
    } catch (e) {
        console.error(e);
        alert("Erro de conexão.");
    }
}

// --- 5. DASHBOARD E ATIVOS ---
async function carregarCarteira() {
    if (!WALLET_ID) return;

    const grid = document.getElementById("assets-grid");
    
    try {
        grid.innerHTML = '<div class="loading">Atualizando cotações...</div>';

        const response = await fetch(`${API_URL}/wallets/${WALLET_ID}/portfolio`);
        if(!response.ok) throw new Error("Falha ao buscar dados");
        
        const dados = await response.json();

        if(dados.length === 0) {
            grid.innerHTML = '<div class="no-data">Nenhum ativo nesta carteira. Adicione uma transação!</div>';
            atualizarResumo(0, 0); 
            return;
        }

        grid.innerHTML = ""; // Limpa loading
        let totalInvestido = 0;
        let totalSaldo = 0;

        dados.forEach(item => {
            totalInvestido += item.totalInvested;
            totalSaldo += item.currentTotalValue;

            // Cores: Verde se lucro >= 0, Vermelho se prejuízo
            const isLucro = item.profitValue >= 0;
            const corTexto = isLucro ? "text-green" : "text-red";
            const sinal = isLucro ? "+" : "";

            const card = document.createElement("div");
            card.className = "asset-card";
            
            // HTML DO CARD (Com Lucro em R$ e %)
            card.innerHTML = `
                <div class="asset-header">
                    <div class="asset-icon">${item.symbol.substring(0,1)}</div>
                    <div class="asset-info">
                        <h3>${item.name}</h3>
                        <span>${item.quantity} ${item.symbol}</span>
                    </div>
                </div>
                
                <div class="asset-values">
                    <div class="main-value">${formatarMoeda(item.currentTotalValue)}</div>
                    
                    <div class="profit-info">
                        <span class="profit-value ${corTexto}">
                            ${sinal} ${formatarMoeda(item.profitValue)}
                        </span>
                        <span class="profit-percent ${corTexto}">
                            (${formatarPorcentagem(item.profitPercentage)})
                        </span>
                    </div>
                </div>
            `;
            grid.appendChild(card);
        });

        atualizarResumo(totalInvestido, totalSaldo);

    } catch (error) {
        console.error(error);
        grid.innerHTML = '<div style="text-align:center; color:#ff4d4d">Erro ao carregar dados.</div>';
    }
}

function atualizarResumo(investido, atual) {
    document.getElementById("saldo-total").innerText = formatarMoeda(atual);
    document.getElementById("total-investido").innerText = formatarMoeda(investido);
    
    let rentabilidadeGeral = 0;
    if(investido > 0) {
        rentabilidadeGeral = ((atual - investido) / investido) * 100;
    }
    
    const elementoLucro = document.getElementById("lucro-total");
    elementoLucro.innerText = formatarPorcentagem(rentabilidadeGeral);
    
    // Muda a classe CSS da pílula (verde ou vermelha)
    elementoLucro.className = rentabilidadeGeral >= 0 ? 'pill green' : 'pill red';
}

// --- 6. TRANSAÇÕES ---
async function enviarTransacao(event) {
    event.preventDefault();

    const symbol = document.getElementById("symbol").value.toUpperCase();
    const quantity = parseFloat(document.getElementById("quantity").value);
    const price = parseFloat(document.getElementById("price").value);
    const type = document.getElementById("type").value;

    // Garante que o ativo existe antes de transacionar
    await garantirAtivo(symbol);

    const payload = {
        walletId: WALLET_ID,
        assetSymbol: symbol,
        quantity: quantity,
        price: price,
        type: type
    };

    try {
        const response = await fetch(`${API_URL}/transactions`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        if(response.ok) {
            alert("Transação realizada com sucesso!");
            fecharModal();
            document.getElementById("form-transacao").reset();
            carregarCarteira(); 
        } else {
            alert("Erro ao salvar transação.");
        }
    } catch (error) {
        alert("Erro de conexão.");
    }
}

async function garantirAtivo(symbol) {
    try {
        await fetch(`${API_URL}/assets`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                symbol: symbol,
                name: symbol,
                type: "CRYPTO"
            })
        });
    } catch (e) {
        // Ignora erro se já existe
    }
}

// --- 7. MODAIS ---
function abrirModal() { document.getElementById("modal-overlay").style.display = "flex"; }
function fecharModal() { document.getElementById("modal-overlay").style.display = "none"; }
function abrirModalCarteira() { document.getElementById("modal-wallet").style.display = "flex"; }
function fecharModalCarteira() { document.getElementById("modal-wallet").style.display = "none"; }