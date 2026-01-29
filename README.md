
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-323330?style=for-the-badge&logo=javascript&logoColor=F7DF1E)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)




> **CriptoCant** é uma aplicação Fullstack para gerenciamento de portfólio de criptomoedas. O sistema permite criar múltiplas carteiras, registrar transações de compra/venda e acompanhar a rentabilidade em tempo real, integrando-se diretamente com o mercado global
## 🗄️ Modelagem de Dados (Database Schema)

O sistema utiliza uma estrutura relacional robusta para garantir a consistência das transações financeiras. Abaixo está o diagrama que representa como as entidades **Carteira**, **Transação** e **Ativo** se relacionam.

### Diagrama Entidade-Relacionamento (DER)
*Gerado via MySQL Workbench*
<img width="623" height="292" alt="image" src="https://github.com/user-attachments/assets/95f7eb94-8558-4044-b7e8-1eb01098c8f9" />
> **Nota Técnica:**
> * A relação entre `Wallet` e `Transaction` é de **One-to-Many (1:N)**: Uma carteira pode ter infinitas transações, mas uma transação pertence a apenas uma carteira.
> * A persistência é gerenciada pelo **Hibernate/JPA**, que cria as tabelas automaticamente baseadas nas Entidades Java.


> ### 🖥️ Dashboard Principal
*Visão geral com a carteira "Black & Gold", rentabilidade em tempo real e ticker de mercado.*
<img width="1919" height="920" alt="image" src="https://github.com/user-attachments/assets/9731654b-ccb7-4419-ad3b-f16004ee610d" />
### 📱 Responsividade e Ativos
*Grid de ativos com cálculo automático de lucro/prejuízo e design responsivo.*
<img width="656" height="193" alt="image" src="https://github.com/user-attachments/assets/c2997ad6-8b56-43ab-9b50-f9b5f3b448d1" />
### ➕ Gerenciamento de Transações
*Modal para adicionar novas compras ou vendas de ativos.*
<img width="378" height="420" alt="image" src="https://github.com/user-attachments/assets/b4874771-535e-49aa-8222-63d03a6eea5c" />

**Sistema Arquitetado De Pastas**
<img width="467" height="285" alt="image" src="https://github.com/user-attachments/assets/953d1ed4-3a55-4152-813a-95c89c07fb4a" />

## 🚀 Funcionalidades

* **Multicarteiras:** Criação e gerenciamento de diferentes carteiras (Ex: "Reserva", "Arriscado").
* **Cotações em Tempo Real:** Integração com a **CoinGecko API** para buscar preços atualizados de Bitcoin, Ethereum, Solana, e podendo adicionar novas moedas internamente.
* **Cálculo de Rentabilidade:** O sistema calcula automaticamente o lucro ou prejuízo (%) baseando-se no preço médio de compra vs. preço atual.
* **UI/UX Premium:** Interface moderna com tema Dark, Glassmorphism e destaque "Black & Gold" para a carteira principal.
* **Arquitetura REST:** Backend desacoplado servindo dados via JSON para o Frontend.

---

## 🛠️ Tecnologias Utilizadas

### Backend (Java)
* **Spring Boot 3:** Framework principal.
* **Spring Data JPA:** Persistência de dados e repositórios.
* **Hibernate:** ORM para mapeamento das entidades (Wallet, Transaction, Asset).
* **Spring Web (RestClient):** Para consumo da API externa da CoinGecko.
* **H2 Database / MySQL:** Banco de dados relacional.

### Frontend
* **HTML5 & CSS3:** Layout flexbox/grid e variáveis CSS para temas.
* **JavaScript (ES6+):** 

---

## ⚙️ Como rodar o projeto

### Pré-requisitos
* Java 17 ou superior.
* Maven.

### Passo a passo
1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/GabsCavalcant/crypto-portfolio-manager.git
    ```
2.  **Entre na pasta:**
    ```bash
    cd criptocant
    ```
3.  **Execute a aplicação:**
    ```bash
    mvn spring-boot:run
    ```
4.  **Acesse no navegador:**
    Abra `http://localhost:8080`

---

## 🧠 Desafios e Aprendizados

Durante o desenvolvimento deste projeto, foquei em resolver problemas reais de engenharia de software:
* **Tratamento de Concorrência na API:** Implementação de **Cache** no serviço de preços para evitar o erro *429 Too Many Requests* da API externa.
* **Stack Overflow no JSON:** Uso correto de `@JsonIgnore` para gerenciar relacionamentos bidirecionais entre Entidades (Carteira <-> Transações).
* **Frontend Vanilla:** Construção de uma SPA (Single Page Application) leve sem depender de frameworks pesados, utilizando JavaScript puro para gerenciar estado e chamadas assíncronas.

---

## 📞 Contato

Desenvolvido por **Gabriel Cavalcante Fernandes**.

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/gabrielcant/)

