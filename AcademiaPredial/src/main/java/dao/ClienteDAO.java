package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;

/**
 *
 * Esta classe DAO permite operações de inserção, leitura, atualização e
 * exclusão na tabela de 'cliente' do banco de dados.
 *
 */

public class ClienteDAO {

	private String AcademiaPredialURL = "jdbc:mysql://localhost/AcademiaPredial";
	private String AcademiaPredialNomeUsuario = "root";
	private String AcademiaPredialSenha = "Favero94@";

	private static final String INSERIR_CLIENTE = "INSERT INTO cliente" + " (nome, apartamento, dataReserva, horario) VALUES " + " (?, ?, ?, ?);";
	private static final String SELECIONAR_CLIENTE = "SELECT codigo, nome, apartamento, dataReserva, horario FROM cliente WHERE codigo = ?";
	private static final String SELECIONAR_CLIENTES = "SELECT * FROM cliente";
	private static final String DELETAR_USUARIO = "DELETE FROM cliente WHERE codigo = ?;";
	private static final String DELETAR_CLIENTE = "UPDATE cliente SET nome = ?, apartamento = ? , dataReserva = ? , horario = ? WHERE codigo = ?;";

	public ClienteDAO() {
	}

	protected Connection getConnection() {
		
		Connection conexao = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexao = DriverManager.getConnection(AcademiaPredialURL, AcademiaPredialNomeUsuario, AcademiaPredialSenha);
		} catch (SQLException erro) {
			erro.printStackTrace();
		} catch (ClassNotFoundException erro) {
			erro.printStackTrace();
		}
		return conexao;
	}

	public void inserirCliente(Cliente cliente) throws SQLException {
		// Fecha automaticamente a conexão após o uso
		try (Connection conexao = getConnection();
				PreparedStatement executarComando = conexao.prepareStatement(INSERIR_CLIENTE)) {
			// O codigo do usuário é omitido do comando, pois foi definido na tabela como
			// autoincremento
			executarComando.setString(1, cliente.getNome());
			executarComando.setString(2, cliente.getApartamento());
			executarComando.setString(3, cliente.getDataReserva());
			executarComando.setString(4, cliente.getHorario());
			System.out.println(executarComando);
			executarComando.executeUpdate();
		} catch (SQLException erro) {
			printSQLException(erro);
		}
	}

	public Cliente selecionarCliente(int codigo) {
		Cliente cliente = null;
		// Etapa 1: estabelece a conexão
		try (Connection conexao = getConnection();
				// Etapa 2: cria o comando da instrução usando o objeto da conexão
				PreparedStatement executarComando = conexao.prepareStatement(SELECIONAR_CLIENTE);) {
			executarComando.setInt(1, codigo);
			System.out.println(executarComando);
			// Etapa 3: executa ou atualiza a query
			ResultSet resultado = executarComando.executeQuery();
			// Etapa 4: processa o objeto ResultSet
			while (resultado.next()) {
				String nome = resultado.getString("nome");
				String apartamento = resultado.getString("apartamento");
				String dataReserva = resultado.getString("dataReserva");
				String horario = resultado.getString("horario");
				cliente = new Cliente(codigo, nome, apartamento, dataReserva, horario);
			}
		} catch (SQLException erro) {
			printSQLException(erro);
		}
		return cliente;
	}

	public List<Object> selecionarClientes() {
		List<Object> usuarios = new ArrayList<>();
		// Código boilerplate
		// Etapa 1: estabelece a conexão
		try (Connection conexao = getConnection();
				// Etapa 2: cria o comando da instrução usando o objeto da conexão
				PreparedStatement executarComando = conexao.prepareStatement(SELECIONAR_CLIENTES);) {
			System.out.println(executarComando);
			// Etapa 3: executa ou atualiza a query
			ResultSet resultado = executarComando.executeQuery();
			// Etapa 4: processa o objeto ResultSet
			while (resultado.next()) {
				int codigo = resultado.getInt("codigo");
				String nome = resultado.getString("nome");
				String apartamento = resultado.getString("apartamento");
				String dataReserva = resultado.getString("dataReserva");
				String horario = resultado.getString("horario");
				usuarios.add(new Cliente(codigo, nome, apartamento, dataReserva, horario));
			}
		} catch (SQLException erro) {
			printSQLException(erro);
		}
		return usuarios;
	}

	public boolean deletarCliente(int codigo) throws SQLException {
		boolean registroDeletado;
		try (Connection conexao = getConnection();
				PreparedStatement executarComando = conexao.prepareStatement(DELETAR_USUARIO);) {
			executarComando.setInt(1, codigo);
			System.out.println(executarComando);
			registroDeletado = executarComando.executeUpdate() > 0;
		}
		return registroDeletado;
	}

	public boolean atualizarCliente(Cliente cliente) throws SQLException {
		boolean registroAtualizado;
		try (Connection connection = getConnection();
				PreparedStatement executarComando = connection.prepareStatement(DELETAR_CLIENTE);) {
			executarComando.setString(1, cliente.getNome());
			executarComando.setString(2, cliente.getApartamento());
			executarComando.setString(3, cliente.getDataReserva());
			executarComando.setString(4, cliente.getHorario());
			executarComando.setString(5, cliente.getcodigo());
			registroAtualizado = executarComando.executeUpdate() > 0;
		}
		return registroAtualizado;
	}

	private void printSQLException(SQLException erro) {
		for (Throwable e : erro) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("Estado do SQL: " + ((SQLException) e).getSQLState());
				System.err.println("Código de erro: " + ((SQLException) e).getErrorCode());
				System.err.println("Mensagem: " + e.getMessage());
				Throwable causa = erro.getCause();
				while (causa != null) {
					System.out.println("Causa: " + causa);
					causa = causa.getCause();
				}
			}
		}
	}
}