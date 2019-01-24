package evento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import Ejecucion.Ejecutar;
import interfazgrafica.PanelEditarMapa;

public class GestionBotonEditorMapa implements ActionListener{

	private PanelEditarMapa editor;
	
	public GestionBotonEditorMapa(PanelEditarMapa editor) {
		this.editor = editor;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(editor.getBotonSeleccionar())) {
			editor.cambiarImagen();
		}else if(e.getSource().equals(editor.getBotonListo())){
			editor.ponerValor();
		}
	}
	
}
