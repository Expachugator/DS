import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ControlSim {

	private HashMap<Integer, CorralID> selecCorrales;
	private int dia;

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

	public Informe realizarSim() {
		dias = usuario.getDia();
		Informe informe = new Informe();
		int e = vc.getE();
		int pr = vc.getPr();
		calcularDatos(e, pr, informe);
		return informe;
	}

	private void calcularDatos(int e, int pr, Informe informe) {
		for (int dia = 0; i < dias; i++) {
			Iterator<CorralID> it = catCorrales.iterator();
			float infectadosExt;
			float sumInfectadosExt;
			while (it.hasNext()) {
				int corralID = it.getCorralID;
				int poblacion = it.getPoblacion(corralID);
				int contagiados = it.getContagiados(corralID);
				if (selecCorrales.contains(corralID)) {
					float porcentaje = calcPorcentaje(poblacion, contagiados);
					informe.newCorralInforme(corralID, poblacion, dia, contagiados, porcentaje);
				}
				int numTraslados = it.getNumTraslados(corralID);
				for (int j = 0; j < numTraslados; j++) {
					int trasladosNum = it.getTrasladosNum(corralID, j);
					int corralIDOri = it.getCorralIDOri(corralID, j);
					int poblacionOri = corrales.get(corralIDOri).getPoblacion();
					int contagiadosOri = corrales.get(corralIDOri).getContagiados();
					infectadosExt = calcInfectados(trasladosNum, poblacionOri, contagiadosOri);
					sumInfectadosExt = sumInfectadosExt(sumInfectadosExt, infectadosExt);
				}
				float totalContagiados = calculaTotal(contagiados, pr, e, poblacion, sumInfectadosExt);
				catCorrales.setContagiados(corralID, totalContagiados);
				if (selecCorrales.contains(corralID)) {
					float porcentaje = calcPorcentaje(poblacion, totalContagiados);
					informe.setDatos(corralID, dia, totalContagiados, porcentaje);
				}
			}
		}
	}

	private float calculaTotal(int contagiados, int pr, int e, int poblacion, float sumInfectadosExt) {
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
