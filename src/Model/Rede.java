package Model;

import java.util.Random;

public class Rede {
	
	 public static final int NUM_SINAIS_ENTRADA = 3;
	  private final double TAXA_APRENDIZAGEM = 0.1;
	  private final double PRECISAO = 0.000001;
	  private final double BETA = 1.0;
	  private final int NUM_NEU_CAMADA_ESCONDIDA = 10;
	  //private final int NUM_NEU_CAMADA_SAIDA = 1;

	  private int numEpocas;
	  private double[] entradas;
	  private double[][] pesosCamadaEscondida;
	  private double[] pesosCamadaSaida;
	  private double[] potencialCamadaEscondida;
	  private double[] saidaCamadaEscondida;
	  private double saida;

	  public Rede() {
		  
		    Random random;

		    entradas = new double[NUM_SINAIS_ENTRADA + 1];
		    pesosCamadaEscondida = new double[NUM_NEU_CAMADA_ESCONDIDA][NUM_SINAIS_ENTRADA + 1];
		    pesosCamadaSaida = new double[NUM_NEU_CAMADA_ESCONDIDA + 1];
		    saidaCamadaEscondida = new double[NUM_NEU_CAMADA_ESCONDIDA + 1];
		    potencialCamadaEscondida = new double[NUM_NEU_CAMADA_ESCONDIDA + 1];

		    //Iniciando os pesos sinpticos
		    random = new Random();
		    for (int i = 0; i < NUM_NEU_CAMADA_ESCONDIDA; i++) {
		      for (int j = 0; j < NUM_SINAIS_ENTRADA + 1; j++) {
		        pesosCamadaEscondida[i][j] = random.nextDouble();
		      }

		      pesosCamadaSaida[i] = random.nextDouble();
		    }
		    pesosCamadaSaida[NUM_NEU_CAMADA_ESCONDIDA] = random.nextDouble();
		    
		  }
	  
	  public String treinar(double amostra[][]) {
		  
		  String log = "";
		  int i;
		  double saidaEsperada;
		  double erroAtual;
		  double erroAnterior;
		  double valorParcial;
		  double gradienteCamadaSaida;
		  double gradienteCamadaEscondida;
		    
		  numEpocas = 0;
		  erroAtual = erroQuadraticoMedio(amostra);
		  log += "Início treinamento.";
		  log += "\n\nÉpoca: " + numEpocas + " - Erro inicial: " + erroAtual;
		 
		  do {
			  this.numEpocas++;
			  erroAnterior = erroAtual;
			  int y = 0;
			  
			  while (y < 6) {
					 
				  i = 0;
				  
				  entradas[0] = -1.0;
		          entradas[1] = amostra[y][i++];
		          entradas[2] = amostra[y][i++];
		          entradas[3] = amostra[y][i++];
		          saidaEsperada = amostra[y][i];
		          
		        //Calculando saidas da camada escondida
		          saidaCamadaEscondida[0] = potencialCamadaEscondida[0] = -1D;
		          for (i = 1; i < saidaCamadaEscondida.length; i++) {
		            valorParcial = 0D;
	
		            for (int j = 0; j < NUM_SINAIS_ENTRADA + 1; j++) {
		              valorParcial += entradas[j] * pesosCamadaEscondida[i - 1][j];
		            }
	
		            potencialCamadaEscondida[i] = valorParcial;
		            saidaCamadaEscondida[i] = funcaoLogistica(valorParcial);
		          }
		          
		        //Calculando saida da camada de saida
		          valorParcial = -1D * pesosCamadaSaida[0];
		          for (i = 1; i < NUM_NEU_CAMADA_ESCONDIDA + 1; i++) {
		            valorParcial += saidaCamadaEscondida[i - 1] * pesosCamadaSaida[i];
		          }
		          saida = funcaoLogistica(valorParcial);
	
		          //Ajustando pesos sinapticos da camada de saida
		          gradienteCamadaSaida = (saidaEsperada - saida) * funcaoLogisticaDerivada(valorParcial);
	
		          for (i = 0; i < pesosCamadaSaida.length; i++) {
		            pesosCamadaSaida[i] = pesosCamadaSaida[i] + (TAXA_APRENDIZAGEM * gradienteCamadaSaida * saidaCamadaEscondida[i]);
		          }
	
		          //Ajustando pesos sinapticos da camada escondida
		          for (i = 0; i < NUM_NEU_CAMADA_ESCONDIDA; i++) {
		            gradienteCamadaEscondida = gradienteCamadaSaida * pesosCamadaSaida[i + 1] * funcaoLogisticaDerivada(potencialCamadaEscondida[i + 1]);
	
		            for (int j = 0; j < NUM_SINAIS_ENTRADA + 1; j++) {
		              pesosCamadaEscondida[i][j] = pesosCamadaEscondida[i][j] + (TAXA_APRENDIZAGEM * gradienteCamadaEscondida * entradas[j]);
		            }
		          }
		          
		          erroAtual = erroQuadraticoMedio(amostra);
 
		          y++;
			  }
			  
			  log += "\nÉpoca: "+ numEpocas + " - Erro atual: " + erroAtual;
			  
		  } while (Math.abs(erroAtual - erroAnterior) > PRECISAO && numEpocas < 10000);
		  
		  
		  log += "\n\nFim treinamento.";
		  
		  return log;
	  }
	  
	  public String testar(double teste[][]) {
		  
		  String log = "";
		  log +="Início teste.";
		  log += "\n\nResposta do teste: ";

		    int i;
		    int numAmostras;
		    double saidaEsperada;
		    double valorParcial;
		    double erroRelativo;
		    double variancia;
		    erroRelativo = 0D;
		    variancia = 0D;
		    numAmostras = 0;
		    int y = 0;
		    
		    while (y < 4) {
		    
			  	  i = 0;
		          numAmostras++;
		          entradas[0] = -1.0;
		          entradas[1] = teste[y][i++];
		          entradas[2] = teste[y][i++];
		          entradas[3] = teste[y][i++];
		          saidaEsperada = teste[y][i];

		          //Calculando saidas da camada escondida
		          saidaCamadaEscondida[0] = potencialCamadaEscondida[0] = -1D;
		          for (i = 1; i < saidaCamadaEscondida.length; i++) {
		            valorParcial = 0D;

		            for (int j = 0; j < NUM_SINAIS_ENTRADA + 1; j++) {
		              valorParcial += entradas[j] * pesosCamadaEscondida[i - 1][j];
		            }

		            potencialCamadaEscondida[i] = valorParcial;
		            saidaCamadaEscondida[i] = funcaoLogistica(valorParcial);
		          }

		          //Calculando saida da camada de saÃ­da
		          valorParcial = -1D * pesosCamadaSaida[0];
		          for (i = 1; i < NUM_NEU_CAMADA_ESCONDIDA + 1; i++) {
		            valorParcial += saidaCamadaEscondida[i - 1] * pesosCamadaSaida[i];
		          }
		          saida = funcaoLogistica(valorParcial);

		          //Porcentagem de erro em cada amostra
		          valorParcial = (100D / saidaEsperada) * (Math.abs(saidaEsperada - saida));
		          erroRelativo += valorParcial;
		          variancia += Math.pow(valorParcial, 2D);
		          
		          log += "\nSaida esperada: " + saidaEsperada + " - Saida: " + saida + " - Valor parcial: " + valorParcial;
				  
		       y++;
		       
		     }
		        
		        //Calculando erro relativo médio
		        erroRelativo = erroRelativo / (double) numAmostras;
		        
		        //Calculando variancia
		        variancia = variancia - ((double)numAmostras * Math.pow(erroRelativo, 2D));
		        variancia = variancia / ((double) (numAmostras - 1));
		        variancia = variancia * 100D;
		        
		        log += "\n\nFim teste.";
		        log += "\nErro relativo médio: " + erroRelativo;
		        log += "\nVariância: " + variancia;
		        
		    return log;
				  
		  }
	  
	  private double erroQuadraticoMedio(double amostra[][]) {
		  
		    int i;
		    int numAmostras;
		    double saidaEsperada;
		    double valorParcial;
		    double erro;
		    erro = 0D;
		    numAmostras = 0;
		    int y = 0;

		      while (y < 6) { 
		    	  
		    	i=0;
		        numAmostras++;
		        entradas[0] = -1.0;
		        entradas[1] = amostra[y][i++];
		        entradas[2] = amostra[y][i++];
		        entradas[3] = amostra[y][i++];
		        saidaEsperada = amostra[y][i++];

		        //Calculando saidas da camada escondida
		        saidaCamadaEscondida[0] = potencialCamadaEscondida[0] = -1D;
		        for (i = 1; i < saidaCamadaEscondida.length; i++) {
		          valorParcial = 0D;

		          for (int j = 0; j < NUM_SINAIS_ENTRADA + 1; j++) {
		            valorParcial += entradas[j] * pesosCamadaEscondida[i - 1][j];
		          }

		          potencialCamadaEscondida[i] = valorParcial;
		          saidaCamadaEscondida[i] = funcaoLogistica(valorParcial);
		        }

		        //Calculando saida da camada de saida
		        valorParcial = -1D * pesosCamadaSaida[0];
		        for (i = 1; i < NUM_NEU_CAMADA_ESCONDIDA + 1; i++) {
		          valorParcial += saidaCamadaEscondida[i - 1] * pesosCamadaSaida[i];
		        }
		        saida = funcaoLogistica(valorParcial);

		        //Calculando erro
		        erro = erro + (Math.pow((saidaEsperada - this.saida), 2D) / 2D);

		        y++;
		      }

		      erro = erro / (double) numAmostras;

		    return erro;
		  }
	  
	  private double funcaoLogistica(double valor) {
		    return 1D / (1D + Math.pow(Math.E, -1D * BETA * valor));
	  }

	  private double funcaoLogisticaDerivada(double valor) {
		  return (BETA * Math.pow(Math.E, -1D * BETA * valor)) / Math.pow((Math.pow(Math.E, -1D * BETA * valor) + 1D), 2D);
	  }
	  
}
