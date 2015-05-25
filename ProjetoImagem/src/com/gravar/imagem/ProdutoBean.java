package com.gravar.imagem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.fileupload.FileUpload;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.gravar.imagem.dao.ProdutoDAO;
import com.gravar.imagem.model.Produto;

@ManagedBean
@SessionScoped
public class ProdutoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private List<Produto> produtos;
	private Produto produto = new Produto();
	private ProdutoDAO produtoDAO;
	
	private String destination = "C:\\tmp\\";
	private UploadedFile file;
	private String nomeDoArquivo;
	

	public ProdutoBean() {
		produtoDAO = new ProdutoDAO();
	}
	public void salvaProduto() {

		try {
			produtoDAO.save(produto);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			produto = new Produto();
			produtos = produtoDAO.listAll();

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Produto adicionado", "Produto adicionado"));
		}
	}
	
	
	public void processFileUpload(FileUploadEvent uploadEvent) {

		String extValidate;
		if (getFile() != null) {
			String ext = getFile().getFileName();
			
			nomeDoArquivo = ext;
			if (ext != null) {
				extValidate = ext.substring(ext.indexOf(".") + 1);
			} else {
				extValidate = "null";
			}

			if (extValidate.equals("jpg") || extValidate.equals("png")) {
				try {
					TransferFile(getFile().getFileName(), getFile()
							.getInputstream());
				} catch (IOException ex) {

					Logger.getLogger(FileUpload.class.getName()).log(
							Level.SEVERE, null, ex);
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage(
							"Perigo, erro ao fazer Upload do arquivo"));
				}
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Sucesso", getFile()
						.getFileName()
						+ "seu upload. conteudo"
						+ getFile().getContentType()
						+ "tamanho"
						+ getFile().getSize()));

			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Perigo",
						"o arquivo tem que ser pdf"));

			}
		} else {

			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Perigo, selecione o arquivo"));
		}
		
		try {
			produto.setImagem(nomeDoArquivo);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void TransferFile(String fileName, InputStream in) {
		try {
			OutputStream out = new FileOutputStream(new File(destination
					+ fileName));
			int reader = 0;
			byte[] bytes = new byte[(int) getFile().getSize()];
			while ((reader = in.read(bytes)) != -1) {
				out.write(bytes, 0, reader);
			}
			in.close();
			out.flush();
			out.close();

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}


	public ProdutoDAO getProdutoDAO() {
		return produtoDAO;
	}

	public void setProdutoDAO(ProdutoDAO produtoDAO) {
		this.produtoDAO = produtoDAO;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	public String getNomeDoArquivo() {
		return nomeDoArquivo;
	}
	public void setNomeDoArquivo(String nomeDoArquivo) {
		this.nomeDoArquivo = nomeDoArquivo;
	}


	
}
