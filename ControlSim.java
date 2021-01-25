import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ControlSim {

	private HashMap<Integer, CorralID> selecCorrales;
	private int dias;
	private VectorContagio vc;
	private HashMap<Integer, CorralID> catCorrales;

	public ControlSim() {
	}

	public void paramSim() {
		selecCorrales = new HashMap<Integer, CorralID>();
		int key = 0;
		while (usuario.getDato != null) {
			CorralID corralID = new CorralID(usuario.getDato);
			selecCorrales.put(key, corralID);
			key++;
		}
	}

	public Informe realizarSim(int dias) {
		dias = dias;
		Informe informe = new Informe();
		float e = vc.getE();
		float pr = vc.getE();
		calcularDatos(e, pr, informe);
		return informe;
	}

	private void calcularDatos(float e, float pr, Informe informe) {
		int numCorrales = catCorrales.size();
		int poblacion;
		int contagiados;
		float porcentaje;
		int numTraslados;
		float infectadosExt;
		float sumInfectadosExt;
		for (int dia = 0; dia < dias; dia++) {
			for (int corralID = 0; corralID < numCorrales; corralID++) {
				poblacion = catCorrales.getPoblacion(corralID);
				contagiados = catCorrales.getContagiados(corralID);
				if (selecCorrales.containsKey(corralID)) {
					float porcentaje = calcPorcentaje(poblacion, contagiados);
					informe.newCorralInforme(corralID, poblacion, dia, contagiados, porcentaje);
				}
				numTraslados = catCorrales.getNumTraslados(corralID);
				for (int traslado = 1; traslado < numTraslados; traslado++) {
					int trasladosNum = catCorrales.getTrasladosNum(corralID, traslado);
					int corralIDOri = catCorrales.getCorralIDOri(corralID, traslado);
					int poblacionOri = catCorrales.getPoblacion(corralIDOri);
					int contagiadosOri = catCorrales.getContagiados(corralIDOri);
					infectadosExt = calcInfectados(trasladosNum, poblacionOri, contagiadosOri);
					sumInfectadosExt = sumInfectadosExt(sumInfectadosExt, infectadosExt);
				}
				float totalContagiados = calculaTotal(contagiados, pr, e, poblacion, sumInfectadosExt);
				catCorrales.setContagiados(corralID, totalContagiados);
				if (selecCorrales.containsKey(corralID)) {
					porcentaje = calcPorcentaje(poblacion, totalContagiados);
					informe.setDatos(corralID, dia, totalContagiados, porcentaje);
				}
			}
		}
	}

	private float calculaTotal(int contagiados, float pr, float e, int poblacion, float sumInfectadosExt) {
		float infectados = contagiados + pr * e * (1 - contagiados / poblacion) * sumInfectadosExt;
		return infectados;
	}

	private float sumInfectadosExt(float sumInfectadosExt, float infectadosExt) {
		sumInfectadosExt = sumInfectadosExt + infectadosExt;
		return sumInfectadosExt;
	}

	private float calcInfectados(int trasladosNum, int poblacionOri, int contagiadosOri) {
		float infectados = trasladosNum * contagiadosOri / poblacionOri;
		return infectados;
	}

	private float calcPorcentaje(int poblacion, float totalContagiados) {
		float porcentaje;
		porcentaje = totalContagiados / poblacion;
		return porcentaje;
	}
}
