package com.gravar.imagem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.fileupload.FileUpload;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class SalvaImagemBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String destination = "C:\\tmp\\";
	private UploadedFile file;


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

	public void upload() {
		String extValidate;
		if (getFile() != null) {
			String ext = getFile().getFileName();
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
}
