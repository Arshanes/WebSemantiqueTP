package tp2;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.semanticweb.owl.align.AlignmentException;
import org.semanticweb.owl.align.AlignmentProcess;

import fr.inrialpes.exmo.align.impl.method.ClassStructAlignment;
import fr.inrialpes.exmo.align.impl.method.EditDistNameAlignment;
import fr.inrialpes.exmo.align.impl.method.NameAndPropertyAlignment;
import fr.inrialpes.exmo.align.impl.method.NameEqAlignment;
import fr.inrialpes.exmo.align.impl.method.SMOANameAlignment;

public class CreateOntologyFilm {

	public static void main(String[] args) {

		try {
			URI ontologyFilm = new File("files/FilmOntology.owl").toURI();
			URI ontologyToulouse = new URI("http://www.irit.fr/~Cassia.Trojahn/insa/FilmsToulouse.owl");

			AlignmentProcess alignment1 = new EditDistNameAlignment();
			AlignmentProcess alignment2 = new SMOANameAlignment();
			AlignmentProcess alignment3 = new NameAndPropertyAlignment();
			AlignmentProcess alignment4 = new ClassStructAlignment();
			AlignmentProcess alignment5 = new NameEqAlignment();

			alignment1.init(ontologyFilm, ontologyToulouse);
			alignment2.init(ontologyFilm, ontologyToulouse);
			alignment3.init(ontologyFilm, ontologyToulouse);
			alignment4.init(ontologyFilm, ontologyToulouse);
			alignment5.init(ontologyFilm, ontologyToulouse);

			alignment1.align(null, new Properties());
			alignment2.align(null, new Properties());
			alignment3.align(null, new Properties());
			alignment4.align(null, new Properties());
			alignment5.align(null, new Properties());

			System.out.println("Num corresp . générées : " + alignment1.nbCells());
			System.out.println("Num corresp . générées : " + alignment2.nbCells());
			System.out.println("Num corresp . générées : " + alignment3.nbCells());
			System.out.println("Num corresp . générées : " + alignment4.nbCells());
			System.out.println("Num corresp . générées : " + alignment5.nbCells());

		} catch (URISyntaxException | AlignmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
