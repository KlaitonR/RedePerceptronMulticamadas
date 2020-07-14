package view;

import Model.Rede;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class PrincipalController {
	
	@FXML TextArea txtAmostra;
	@FXML TextArea txtTeste;
	
	public Rede rede = new Rede();
	private double[][] amostra = new double [6][4];
	private double[][] teste = new double [4][4];
	
	@FXML
	public void treinar() {
		
		String txt = "";
		carregarAmostra(amostra);
		txt = rede.treinar(amostra);
		txtAmostra.setText(txt);
	}
	
	@FXML
	public void testar() {
		
		String txt = "";
		carregarTeste(teste);
		txt = rede.testar(teste);
		txtTeste.setText(txt);
	}
	
	public double[][] carregarAmostra(double amostra[][]) {
		
		amostra [0][0] = 0.8799;
	    amostra [0][1] = 0.7998;
	    amostra [0][2] = 0.3972;
	    amostra [0][3] = 0.8399;
	    
	    amostra [1][0] = 0.5700;
	    amostra [1][1] = 0.5111;
	    amostra [1][2] = 0.2418;
	    amostra [1][3] = 0.6258;
	    
	    amostra [2][0] = 0.6796;
	    amostra [2][1] = 0.4117;
	    amostra [2][2] = 0.3370;
	    amostra [2][3] = 0.6622;
	    
	    amostra [3][0] = 0.3567;
	    amostra [3][1] = 0.2967;
	    amostra [3][2] = 0.6037;
	    amostra [3][3] = 0.5969;
	    
	    amostra [4][0] = 0.3866;
	    amostra [4][1] = 0.8390;
	    amostra [4][2] = 0.0232;
	    amostra [4][3] = 0.5316;
	    
	    amostra [5][0] = 0.0271;
	    amostra [5][1] = 0.7788;
	    amostra [5][2] = 0.7445;
	    amostra [5][3] = 0.6335;
	    
	    return amostra;
	}
	
	public double[][] carregarTeste(double teste[][]) {
		
		teste[0][0] = 0.0611;
	    teste[0][1] = 0.2860;
	    teste[0][2] = 0.7464;
	    teste[0][3] = 0.4831;
	    
	    teste[1][0] = 0.5102;
	    teste[1][1] = 0.7464;
	    teste[1][2] = 0.0860;
	    teste[1][3] = 0.5965;
	    
	    teste[2][0] = 0.0004;
	    teste[2][1] = 0.6916;
	    teste[2][2] = 0.5006;
	    teste[2][3] = 0.5318;
	    
	    teste[3][0] = 0.9430;
	    teste[3][1] = 0.4476;
	    teste[3][2] = 0.2648;
	    teste[3][3] = 0.6843;
	    
	    return teste;
	}

}
