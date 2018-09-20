package com.msantos.agileshop;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.msantos.agileshop.domain.Categoria;
import com.msantos.agileshop.domain.Cidade;
import com.msantos.agileshop.domain.Cliente;
import com.msantos.agileshop.domain.Endereco;
import com.msantos.agileshop.domain.Estado;
import com.msantos.agileshop.domain.Pagamento;
import com.msantos.agileshop.domain.PagamentoBoleto;
import com.msantos.agileshop.domain.PagamentoCartao;
import com.msantos.agileshop.domain.Pedido;
import com.msantos.agileshop.domain.Produto;
import com.msantos.agileshop.domain.enums.EstadoPagamento;
import com.msantos.agileshop.domain.enums.TipoCliente;
import com.msantos.agileshop.repositories.CategoriaRepository;
import com.msantos.agileshop.repositories.CidadeRepository;
import com.msantos.agileshop.repositories.ClienteRepository;
import com.msantos.agileshop.repositories.EnderecoRepository;
import com.msantos.agileshop.repositories.EstadoRepository;
import com.msantos.agileshop.repositories.PagamentoRepository;
import com.msantos.agileshop.repositories.PedidoRepository;
import com.msantos.agileshop.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null,"Computador", 2000.00);
		Produto p2 = new Produto(null,"Impressora", 800.00);
		Produto p3 = new Produto(null,"Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null,"Umberlândia", est1);
		Cidade c2 = new Cidade(null,"Campinas", est2);
		Cidade c3 = new Cidade(null,"São Paulo", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "Maria@gmail.com", "081.589.897-55", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("8199999555","8199955887"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "51345-091", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "05144-911", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/07/2018 00:00"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 00:00"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoCartao(null, EstadoPagamento.PAGO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
	}
}
