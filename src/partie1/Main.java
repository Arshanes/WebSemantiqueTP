package partie1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.AlignmentException;
import org.semanticweb.owl.align.AlignmentProcess;
import org.semanticweb.owl.align.AlignmentVisitor;

import fr.inrialpes.exmo.align.impl.eval.PRecEvaluator;
import fr.inrialpes.exmo.align.impl.method.ClassStructAlignment;
import fr.inrialpes.exmo.align.impl.method.EditDistNameAlignment;
import fr.inrialpes.exmo.align.impl.method.NameAndPropertyAlignment;
import fr.inrialpes.exmo.align.impl.method.NameEqAlignment;
import fr.inrialpes.exmo.align.impl.method.SMOANameAlignment;
import fr.inrialpes.exmo.align.impl.renderer.RDFRendererVisitor;
import fr.inrialpes.exmo.align.parser.AlignmentParser;

public class Main {

	public static void main(String[] args) {

		try {
			generateAlign();
		} catch (URISyntaxException | AlignmentException | FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static void generateAlign()
			throws URISyntaxException, AlignmentException, FileNotFoundException, UnsupportedEncodingException {
		URI onto1 = new URI("http://oaei.ontologymatching.org/tests/101/onto.rdf");
		URI onto2 = new URI("http://oaei.ontologymatching.org/tests/302/onto.rdf");
		AlignmentProcess alignment1 = new EditDistNameAlignment();
		AlignmentProcess alignment2 = new SMOANameAlignment();
		AlignmentProcess alignment3 = new NameAndPropertyAlignment();
		AlignmentProcess alignment4 = new ClassStructAlignment();
		AlignmentProcess alignment5 = new NameEqAlignment();

		alignment1.init(onto1, onto2);
		alignment2.init(onto1, onto2);
		alignment3.init(onto1, onto2);
		alignment4.init(onto1, onto2);
		alignment5.init(onto1, onto2);

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

		render(alignment1);
		evaluate(alignment1);

		render(alignment2);
		evaluate(alignment2);

		render(alignment3);
		evaluate(alignment3);

		render(alignment4);
		evaluate(alignment4);

		render(alignment5);
		evaluate(alignment5);
	}

	public static void render(Alignment alignment)
			throws FileNotFoundException, UnsupportedEncodingException, AlignmentException {
		PrintWriter writer;
		FileOutputStream f = new FileOutputStream(new File("files/" + alignment.getClass().getSimpleName() + ".rdf"));
		writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(f, "UTF-8")), true);
		AlignmentVisitor renderer = new RDFRendererVisitor(writer);
		alignment.render(renderer);
		writer.flush();
		writer.close();
	}

	public static void evaluate(Alignment alignment) throws URISyntaxException, AlignmentException {
		URI reference = new URI("http://oaei.ontologymatching.org/tests/302/refalign.rdf");
		AlignmentParser aparser = new AlignmentParser(0);
		Alignment refalign = aparser.parse(reference);
		PRecEvaluator evaluator = new PRecEvaluator(refalign, alignment);
		evaluator.eval(new Properties());
		System.out.println(" Precision : " + evaluator.getPrecision());
		System.out.println(" Recall :" + evaluator.getRecall());
		System.out.println(" FMeasure :" + evaluator.getFmeasure());
	}
}
