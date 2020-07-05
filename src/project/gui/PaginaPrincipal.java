package project.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.TextField;
import java.awt.SystemColor;
import java.awt.Font;

@SuppressWarnings("serial")
public class PaginaPrincipal extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PaginaPrincipal frame = new PaginaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PaginaPrincipal() {
		setResizable(false);
		
		setTitle("(De)codificacion de Base64");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 166);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		TextField fileOriginalFormat = new TextField();
		fileOriginalFormat.setFont(new Font("Arial", Font.PLAIN, 12));
		fileOriginalFormat.setBackground(SystemColor.text);
		fileOriginalFormat.setText("Ubicacion del archivo original");
		fileOriginalFormat.setBounds(10, 20, 167, 22);
		contentPane.add(fileOriginalFormat);
		
		TextField fileEncode = new TextField();
		fileEncode.setFont(new Font("Arial", Font.PLAIN, 12));
		fileEncode.setBackground(SystemColor.text);
		fileEncode.setText("Ubicacion final");
		fileEncode.setBounds(10, 61, 167, 22);
		contentPane.add(fileEncode);
		
		TextField fileEncodeFormat = new TextField();
		fileEncodeFormat.setFont(new Font("Arial", Font.PLAIN, 12));
		fileEncodeFormat.setBackground(SystemColor.text);
		fileEncodeFormat.setText("Ubicacion del archivo Base64");
		fileEncodeFormat.setBounds(264, 20, 167, 22);
		contentPane.add(fileEncodeFormat);
		
		TextField fileDecode = new TextField();
		fileDecode.setFont(new Font("Arial", Font.PLAIN, 12));
		fileDecode.setBackground(SystemColor.text);
		fileDecode.setText("Ubicacion final");
		fileDecode.setBounds(264, 61, 167, 22);
		contentPane.add(fileDecode);
		
		//Boton Codificar aBase64
		JButton btnCodificarABase = new JButton("Codificar a Base64");
		btnCodificarABase.setEnabled(false);
		btnCodificarABase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					encodeFile(fileOriginalFormat.getText(), fileEncode.getText());
				} catch (IOException error) {
					msg("Ocurrió un error al convertir el archivo. Revisa si la ruta\n" + 
							"especificada existe o prueba contactar con el administrador");
				}
			}
		});
		btnCodificarABase.setBackground(SystemColor.menu);
		btnCodificarABase.setBounds(10, 95, 210, 23);
		contentPane.add(btnCodificarABase);
		
		//Boton btnSave64
		JButton btnSave64 = new JButton("...");
		btnSave64.setEnabled(false);
		btnSave64.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonActionPerformed(e, "folder", fileEncode, btnCodificarABase);
			}
		});
		btnSave64.setBackground(SystemColor.menu);
		btnSave64.setBounds(183, 61, 37, 23);
		contentPane.add(btnSave64);
		
		//Boton btnChooseFile
		JButton btnChooseFile = new JButton("...");
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonActionPerformed(e, null, fileOriginalFormat, btnSave64);
			}
		});
		btnChooseFile.setBackground(SystemColor.menu);
		btnChooseFile.setBounds(183, 20, 37, 23);
		contentPane.add(btnChooseFile);
		
		//Boton Decodificar de Base64
		JButton btnDecodificarDeBase = new JButton("Decodificar de Base64");
		btnDecodificarDeBase.setEnabled(false);
		btnDecodificarDeBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					decodeFile(fileEncodeFormat.getText(), fileDecode.getText());
				} catch (IOException error) {
					msg("Ocurrió un error al convertir el archivo. Revisa si la ruta\n" + 
							"especificada existe o prueba contactar con el administrador");
				}
			}
		});
		btnDecodificarDeBase.setBackground(SystemColor.menu);
		btnDecodificarDeBase.setBounds(264, 95, 210, 23);
		contentPane.add(btnDecodificarDeBase);
		
		//Boton btnSaveFile
		JButton btnSaveFile = new JButton("...");
		btnSaveFile.setEnabled(false);
		btnSaveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonActionPerformed(e, "folder", fileDecode, btnDecodificarDeBase);
			}
		});
		btnSaveFile.setBackground(SystemColor.menu);
		btnSaveFile.setBounds(437, 61, 37, 23);
		contentPane.add(btnSaveFile);
		
		//Boton btnChoose64
		JButton btnChoose64 = new JButton("...");
		btnChoose64.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonActionPerformed(e, "txt", fileEncodeFormat, btnSaveFile);
			}
		});
		btnChoose64.setBackground(SystemColor.menu);
		btnChoose64.setBounds(437, 20, 37, 23);
		contentPane.add(btnChoose64);
	}
	
	private void msg(String texto) {
		JOptionPane.showMessageDialog(this, texto, "Error de Conversión", JOptionPane.WARNING_MESSAGE);
	}
	
	private void jButtonActionPerformed(ActionEvent event, String type, TextField pathText, JButton activarBoton) {
		//Creamos el objeto JFileChooser
		JFileChooser fc = new JFileChooser();
		
		if(type != null) {
			FileNameExtensionFilter filtro = new FileNameExtensionFilter("*." + type, type);
			
			fc.setFileFilter(filtro);
			
			if(type == "folder") {
				 fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			}
		}
		//Abrimos la ventana, guardamos la opcion seleccionada por el usuario
		int seleccion = fc.showOpenDialog(this);
		
		//Si el usuario, pincha en aceptar
		if(seleccion == JFileChooser.APPROVE_OPTION) {
			
			//Seleccionamos el fichero
			File fichero = fc.getSelectedFile();
			
			activarBoton.setEnabled(true);
			
			JOptionPane.showMessageDialog(this, "Archivo seleccionado con éxito");
			
			//Escribe la ruta del fichero seleccionado en el campo de texto
			pathText.setText(fichero.getAbsolutePath());
		}
	}
	
	private void encodeFile(String pathArchivo, String folderSalida) throws IOException {
		//FileInputStream fileStream = new FileInputStream(pathArchivo);
		byte[] datos = Files.readAllBytes(Paths.get(pathArchivo));
		String fileString = Base64.getEncoder().encodeToString(datos);
		
		File file = new File(pathArchivo);
		folderSalida = folderSalida + "\\" +  file.getName() + ".txt";
		FileWriter fileWriter = new FileWriter(folderSalida);
		
		fileWriter.write(fileString);
		fileWriter.close();
		
		JOptionPane.showMessageDialog(this, "Archivo convertido con éxito.");
		JOptionPane.showMessageDialog(this, "Revísalo en " + folderSalida);
		//fileStream.close();
		
		//return fileString;
	}
	
	private void decodeFile(String pathBaseText, String folderSalida) throws IOException {
		
	    String archivo = new String(Files.readAllBytes(Paths.get(pathBaseText)));
		byte[] datos = Base64.getDecoder().decode(archivo);
		//byte[] datos = Files.readAllBytes(Paths.get(pathBaseText)); esto no funciona
		//String fileString = Base64.getEncoder().encodeToString(datos); esto no funciona
		
		File file = new File(pathBaseText.replaceFirst(".txt", ""));//este es el archivo doc1.docx
		folderSalida = folderSalida + "\\" +  file.getName();
		FileOutputStream fileOutputStream = new FileOutputStream(folderSalida);
		fileOutputStream.write(datos);
		
		fileOutputStream.close();
		
		JOptionPane.showMessageDialog(this, "Archivo convertido con éxito.");
		JOptionPane.showMessageDialog(this, "Revísalo en " + folderSalida);
		
	}
}