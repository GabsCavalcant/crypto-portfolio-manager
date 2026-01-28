package com.gabriel.cryptodashboard.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.cryptodashboard.entidades.Asset;
import com.gabriel.cryptodashboard.service.AssetService;

@RestController
@RequestMapping("/assets")
public class AssetController {

	private final AssetService assetService;

	public AssetController(AssetService assetService) {

		this.assetService = assetService;
	}

	@GetMapping
	public List<Asset> listarTodos() {
		return assetService.findAll();
	}

	@PostMapping
	public Asset cadastrar(@RequestBody Asset asset) {
		return assetService.save(asset);

	}
}
