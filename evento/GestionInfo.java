package evento;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import interfazgrafica.*;


public class GestionInfo implements ActionListener{
	private PanelInfo info;

	public GestionInfo(PanelInfo info) {
		this.info = info;
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		try {
			info.setCasillaInfo((BotonCasilla) evento.getSource());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	


}
