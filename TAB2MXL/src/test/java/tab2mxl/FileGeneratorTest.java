package tab2mxl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


class FileGeneratorTest {

	static FileGenerator fileGen;
	String fileContent;
	String expected;
	
	@BeforeAll
	static void setUp() {
		fileGen = new FileGenerator("Outputs/tester.musicxml", false);
	}
	
	@Test
	void testFileGenerator() {
		openWriter();
		assertNotNull(fileGen.myWriter);
		assertNotNull(fileGen.saveFile);
		assertNotNull(fileGen.LAST_USED_FOLDER_CONVERT);
		assertNotNull(fileGen.prefs);
	}

	@Test
	void testAddStringInfo1() {
		openWriter();
		fileGen.addStringInfo("Example Title", "Guitar");
		fileGen.end();
		
		fileContent = this.readFile();
		expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<score-partwise version=\"3.1\">\n"
				+ "  <work>\n"
				+ "    <work-title>Example Title</work-title>\n"
				+ "  </work>\n"
				+ "  <part-list>\n"
				+ "    <score-part id=\"P1\">\n"
				+ "      <part-name>Guitar</part-name>\n"
				+ "    </score-part>\n"
				+ "    <score-part id=\"P2\">\n"
				+ "      <part-name>Guitar</part-name>\n"
				+ "    </score-part>\n"
				+ "  </part-list>\n"
				+ "</score-partwise>";
		
		assertEquals(expected, fileContent);
	}
	

	@Test
	void testAddStringInfo2() {
		openWriter();
		fileGen.addStringInfo("", "Guitar");
		fileGen.end();
		
		fileContent = this.readFile();
		expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<score-partwise version=\"3.1\">\n"
				+ "  <work>\n"
				+ "    <work-title></work-title>\n"
				+ "  </work>\n"
				+ "  <part-list>\n"
				+ "    <score-part id=\"P1\">\n"
				+ "      <part-name>Guitar</part-name>\n"
				+ "    </score-part>\n"
				+ "    <score-part id=\"P2\">\n"
				+ "      <part-name>Guitar</part-name>\n"
				+ "    </score-part>\n"
				+ "  </part-list>\n"
				+ "</score-partwise>";
		
		assertEquals(expected, fileContent);
	}
	
	@Test
	void testAddInfo3() {
		openWriter();
		fileGen.addStringInfo(" ", "Guitar");
		fileGen.end();
		
		fileContent = this.readFile();
		expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<score-partwise version=\"3.1\">\n"
				+ "  <work>\n"
				+ "    <work-title> </work-title>\n"
				+ "  </work>\n"
				+ "  <part-list>\n"
				+ "    <score-part id=\"P1\">\n"
				+ "      <part-name>Guitar</part-name>\n"
				+ "    </score-part>\n"
				+ "    <score-part id=\"P2\">\n"
				+ "      <part-name>Guitar</part-name>\n"
				+ "    </score-part>\n"
				+ "  </part-list>\n"
				+ "</score-partwise>";
		
		assertEquals(expected, fileContent);
	}

	@Test
	void testOpenPart() {
		openWriter();
		fileGen.openPart(1);
		fileGen.end();
		String fileContent = this.readFile();
		
		String expected = "<part id=\"P" + 1 + "\">\n"
				+ "</score-partwise>";

		assertEquals(expected, fileContent); 
	}
	
	@Test
	void testOpenPart2() {
		openWriter();
		fileGen.openPart(3);
		fileGen.end();
		String fileContent = this.readFile();
		
		String expected = "<part id=\"P" + 3 + "\">\n"
				+ "</score-partwise>";

		assertEquals(expected, fileContent); 
	}

	@Test
	void testClosePart() {
		openWriter();
		fileGen.closePart();
		fileGen.end();
		String fileContent = this.readFile();
		
		String expected = "</part>\n"
				+ "</score-partwise>";
		
		assertEquals(expected, fileContent); 
	}

	@Test
	void testStringAttributes() {
		openWriter();	
		String[] tune = new String[]{"E", "B", "G", "D", "A", "E"};	
		int[] oct = new int[] {1, 2, 3, 4, 5, 6};
		fileGen.stringAttributes(2,4,4, "G", tune, oct);
		fileGen.end();
		String fileContent = this.readFile();
		String expected = "<attributes>\n"
				+ "  <divisions>2</divisions>\n"
				+ "  <key>\n"
				+ "    <fifths>0</fifths>\n"
				+ "    <mode>major</mode>\n"
				+ "  </key>\n"
				+ "  <time>\n"
				+ "    <beats>4</beats>\n"
				+ "    <beat-type>4</beat-type>\n"
				+ "  </time>\n"
				+ "  <clef>\n"
				+ "    <sign>G</sign>\n"
				+ "    <line>2</line>\n"
				+ "    <clef-octave-change>-1</clef-octave-change>\n"
				+ "  </clef>\n"
				+ "  <staff-details>\n"
				+ "    <staff-lines>6</staff-lines>\n"
				+ "    <staff-tuning line=\"1\">\n"
				+ "      <tuning-step>E</tuning-step>\n"
				+ "      <tuning-octave>6</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "    <staff-tuning line=\"2\">\n"
				+ "      <tuning-step>A</tuning-step>\n"
				+ "      <tuning-octave>5</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "    <staff-tuning line=\"3\">\n"
				+ "      <tuning-step>D</tuning-step>\n"
				+ "      <tuning-octave>4</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "    <staff-tuning line=\"4\">\n"
				+ "      <tuning-step>G</tuning-step>\n"
				+ "      <tuning-octave>3</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "    <staff-tuning line=\"5\">\n"
				+ "      <tuning-step>B</tuning-step>\n"
				+ "      <tuning-octave>2</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "    <staff-tuning line=\"6\">\n"
				+ "      <tuning-step>E</tuning-step>\n"
				+ "      <tuning-octave>1</tuning-octave>\n"
				+ "    </staff-tuning>\n"
				+ "  </staff-details>\n"
				+ "</attributes>\n"
				+ "</score-partwise>";
		
		assertEquals(expected, fileContent); 
	}

	@Test
	void testAddStringNote() {
		setUp();
		fileGen.addStringNote(1,1,'E', "half", 1, 3, 0,false,false,false,false,false,false);

		fileGen.end();
		String fileContent = this.readFile();
		String expected = "<note>\n"
				+ "  <pitch>\n"
				+ "    <step>E</step>\n"
				+ "    <octave>3</octave>\n"
				+ "  </pitch>\n"
				+ "  <duration>1</duration>\n"
				+ "  <type>half</type>\n"
				+ "  <notations>\n"
				+ "    <technical>\n"
				+ "      <string>1</string>\n"
				+ "      <fret>1</fret>\n"
				+ "    </technical>\n"
				+ "  </notations>\n"
				+ "</note>\n"
				+ "</score-partwise>";
		assertEquals(expected, fileContent); 
	}
	
	@Test
	void testAddStringNote2() {
		openWriter();
		fileGen.addStringNote(2,3,'G', "quarter", 1, 2, 0,false,false,false,false,false,false);
		fileGen.end();
		String fileContent = this.readFile();
		String expected = "<note>\n"
				+ "  <pitch>\n"
				+ "    <step>G</step>\n"
				+ "    <octave>2</octave>\n"
				+ "  </pitch>\n"
				+ "  <duration>1</duration>\n"
				+ "  <type>quarter</type>\n"
				+ "  <notations>\n"
				+ "    <technical>\n"
				+ "      <string>2</string>\n"
				+ "      <fret>3</fret>\n"
				+ "    </technical>\n"
				+ "  </notations>\n"
				+ "</note>\n"
				+ "</score-partwise>";
		assertEquals(expected, fileContent); 
	}

	@Test

	void testAddStringChord() {
		openWriter();
		char[] notes = new char[] {'E','B'};		
		int[] frets = new int[] {1,2};
		int[] lines = new int[] {1,2};
		int[] oct = new int[] {1,2,3};
		int[] dot = new int[] {1,2,3};
		boolean[] alter = new boolean[] {false,false};
		boolean[] harmonic = new boolean[] {false,false};
		fileGen.addStringChord(notes, "half", 1,oct,frets,lines, dot,alter,-1,false,false,false,harmonic);
		fileGen.end();
		
		String fileContent = this.readFile();
		String expected = 
				"<note>\n"
				+ "  <pitch>\n"
				+ "    <step>B</step>\n"
				+ "    <octave>2</octave>\n"
				+ "    </pitch>\n"
				+ "  <duration>1</duration>\n"
				+ "  <type>half</type>\n"
				+ "  <dot/>\n"
				+ "  <dot/>\n"
				+ "  <notations>\n"
				+ "    <technical>\n"
				+ "      <string>2</string>\n"
				+ "      <fret>2</fret>\n"
				+ "    </technical>\n"
				+ "  </notations>\n"
				+ "</note>\n"
				+ "<note>\n"
				+ "  <chord/>\n"
				+ "  <pitch>\n"
				+ "    <step>E</step>\n"
				+ "    <octave>1</octave>\n"
				+ "    </pitch>\n"
				+ "  <duration>1</duration>\n"
				+ "  <type>half</type>\n"
				+ "  <dot/>\n"
				+ "  <notations>\n"
				+ "    <technical>\n"
				+ "      <string>1</string>\n"
				+ "      <fret>1</fret>\n"
				+ "    </technical>\n"
				+ "  </notations>\n"
				+ "</note>\n"
				+ "</score-partwise>";
				
				
				
		assertEquals(expected, fileContent); 
	    }

	@Test
	void testOpenMeasure() {
		openWriter();
		fileGen.openMeasure(1, false, 0);
		fileGen.end();
		String fileContent = this.readFile();
		String expected = "<measure number=\"" + 1 + "\">\n" + "</score-partwise>";
		assertEquals(expected, fileContent); 
	}
	
	@Test
	void testOpenMeasure2() {
		openWriter();
		fileGen.openMeasure(4, false, 0);
		fileGen.end();
		String fileContent = this.readFile();
		String expected = "<measure number=\"" + 4 + "\">\n" + "</score-partwise>";
		assertEquals(expected, fileContent); 
	}
	
	@Test
	void testOpenMeasure3() {
		openWriter();
		fileGen.openMeasure(0, false, 0);
		fileGen.end();
		String fileContent = this.readFile();
		String expected = "<measure number=\"" + 0 + "\">\n" + "</score-partwise>";
		assertEquals(expected, fileContent); 
		//fail("Measure 0 should throw error");
	}
	
	@Test
	void testOpenMeasureRepeat() {
		openWriter();
		fileGen.openMeasure(0, true, 5);
		fileGen.end();
		String fileContent = this.readFile();
		String expected = "<measure number=\"" + 0 + "\">\n"
						+ "  <barline location=\"left\">\n"
						+ "    <bar-style>heavy-light</bar-style>\n"
						+ "    <repeat direction=\"forward\" times=\"" + 5 + "\"/>\n"
						+ "  </barline>\n"
						+ "  <direction placement=\"above\">\n"
						+ "    <direction-type>\n"
						+ "      <words default-x=\"15\" default-y=\"15\" font-size=\"9\" font-style=\"italic\">Repeat " + 5 + "x</words>\n"
						+ "    </direction-type>\n"
						+ "  </direction>\n" 
						+ "</score-partwise>";
		assertEquals(expected, fileContent); 
		//fail("Measure 0 should throw error");
	}

	@Test
	void testCloseMeasure() {
		openWriter();
		fileGen.closeMeasure(false, 0);
		fileGen.end();
		String fileContent = this.readFile();
		String expected =  "</measure>\n" 
				 + "</score-partwise>";
		assertEquals(expected, fileContent); 
	}

	@Test
	void testEnd() {
		openWriter();
		fileGen.end();
		String fileContent = this.readFile();
		String expected = "</score-partwise>";
		assertEquals(expected, fileContent); 
	}
	
	@Test
	void testCurrentIndent() {
		openWriter();
		fileGen.addStringInfo("Title Example", "Guitar");
		fileGen.openMeasure(1, false, 0);
		fileGen.closeMeasure(false, 0);
		fileGen.end();
		String fileContent = this.readFile();
		
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<score-partwise version=\"3.1\">\n"
				+ "  <work>\n"
				+ "    <work-title>Title Example</work-title>\n"
				+ "  </work>\n"
				+ "  <part-list>\n"
				+ "    <score-part id=\"P1\">\n"
				+ "      <part-name>Guitar</part-name>\n"
				+ "    </score-part>\n"
				+ "    <score-part id=\"P2\">\n"
				+ "      <part-name>Guitar</part-name>\n"
				+ "    </score-part>\n"
				+ "  </part-list>\n"
				+ "  <measure number=\"" + 1 + "\">\n" 
				+ "  </measure>\n" 
				+ "</score-partwise>";
		
		assertEquals(expected, fileContent); 
		
	}
	
	@SuppressWarnings("resource")
	String readFile() {
		String fileContent = null;
		try {
			fileContent = new Scanner(new File("Outputs/tester.musicxml")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fileContent;
	}	
	
	void openWriter(){
		try {
			fileGen.currentIndent = "";
			fileGen.myWriter = new FileWriter(fileGen.saveFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
