package model;

public class Cliente {
	protected int codigo;
	protected String nome;
	protected String apartamento;
	protected String dataReserva;
	protected String horario;

	public Cliente() {
	}

	public Cliente(String nome, String apartamento, String dataReserva, String horario) {
		super();
		this.nome = nome;
		this.apartamento = apartamento;
		this.dataReserva = dataReserva;
		this.horario = horario;

	}

	public Cliente(int codigo, String nome, String dataReserva, String horario, String apartamento) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.apartamento = apartamento;
		this.dataReserva = dataReserva;
		this.horario = horario;
		
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApartamento() {
		return apartamento;
	}

	public void setApartamento(String apartamento) {
		this.apartamento = apartamento;
	}
	
	public String getDataReserva() {
		return dataReserva;
	}

	public void setDataReserva(String dataReserva) {
		this.dataReserva = dataReserva;
	}
	
	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	}