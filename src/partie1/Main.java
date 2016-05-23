package partie1;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.semanticweb.owl.align.AlignmentException;
import org.semanticweb.owl.align.AlignmentProcess;

import fr.inrialpes.exmo.align.impl.method.NameEqAlignment;

public class Main {

	public static void main(String[] args) {

		try {
			generateAlign();
		} catch (URISyntaxException | AlignmentException e) {
			e.printStackTrace();
		}
	}

	public static void generateAlign() throws URISyntaxException, AlignmentException {
		URI onto1 = new URI("http://oaei.ontologymatching.org/tests/101/onto.rdf");
		URI onto2 = new URI("http://oaei.ontologymatching.org/tests/302/onto.rdf");
		AlignmentProcess alignment = new NameEqAlignment();
		alignment.init(onto1, onto2);
		alignment.align(null, new Properties());
		System.out.println("Num corresp . générées : " + alignment.nbCells());
	}
}
