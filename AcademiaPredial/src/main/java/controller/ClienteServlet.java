package controller;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClienteDAO;
import model.Cliente;


 @WebServlet("/")
public class ClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClienteDAO clienteDAO;

	public void init() {
		clienteDAO = new ClienteDAO();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getServletPath();

		try {
			switch (acao) {
			case "/novo":
				exibirFormularioInclusao(request, response);
				break;
			case "/inserir":
				inserirCliente(request, response);
				break;
			case "/deletar":
				deletarCliente(request, response);
				break;
			case "/editar":
				exibirFormularioEdicao(request, response);
				break;
			case "/atualizar":
				atualizarCliente(request, response);
				break;
			default:
				listarCliente(request, response);
				break;
			}
		} catch (SQLException erro) {
			throw new ServletException(erro);
		}
	}

	private void listarCliente(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<?> listarCliente = clienteDAO.selecionarClientes();
		request.setAttribute("listarCliente", listarCliente);
		RequestDispatcher renderizar = request.getRequestDispatcher("listagem-cliente.jsp");
		renderizar.forward(request, response);
	}

	private void exibirFormularioInclusao(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher renderizar = request.getRequestDispatcher("formulario-cliente.jsp");
		renderizar.forward(request, response);
	}

	private void exibirFormularioEdicao(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int codigo = Integer.parseInt(request.getParameter("codigo"));
		Cliente selecaoCliente = clienteDAO.selecionarCliente(codigo);
		RequestDispatcher renderizar = request.getRequestDispatcher("formulario-cliente.jsp");
		request.setAttribute("cliente", selecaoCliente);
		renderizar.forward(request, response);
	}

	private void inserirCliente(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		String nome = request.getParameter("nome");
		String apartamento = request.getParameter("apartamento");
		String dataReserva = request.getParameter("dataReserva");
		String horario = request.getParameter("horario");
		Cliente adicaoCliente = new Cliente(nome, apartamento, dataReserva, horario);
		clienteDAO.inserirCliente(adicaoCliente);
		response.sendRedirect("listagem");
	}

	private void atualizarCliente(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int codigo = Integer.parseInt(request.getParameter("codigo"));
		String nome = request.getParameter("nome");
		String apartamento = request.getParameter("apartamento");
		String dataReserva = request.getParameter("dataReserva");
		String horario = request.getParameter("horario");
		Cliente adicaoCliente = new Cliente(codigo, nome, apartamento, dataReserva, horario);
		clienteDAO.inserirCliente(adicaoCliente);
		response.sendRedirect("listagem");
	}

	private void deletarCliente(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int codigo = Integer.parseInt(request.getParameter("codigo"));
		clienteDAO.deletarCliente(codigo);
		response.sendRedirect("listagem");
	}

}