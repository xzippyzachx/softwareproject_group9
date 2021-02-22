package tab2mxl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParserTest {

	@BeforeAll
	static void setUp() {
		File file = new File("TestInput_Guitar1.txt");
	}
	
	//@Test
	void testParser() {
		
		String[] inputText = new String [6];
		 int index = 0;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("TestInput_Guitar1.txt"));
			String line = reader.readLine();
			while (line != null) {
				inputText[index] = line;
				line = reader.readLine();
				index++;
			}
					
		} catch (IOException e) {
			e.printStackTrace();
		}
		
     ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
     
		for (String line : inputText) {	
			System.out.println(line);
			String[] lineInput = line.split("");
			ArrayList<String> lineInputList = new ArrayList<String>();
			
			for(String character : lineInput) {
				lineInputList.add(character);
		    }
			input.add(lineInputList);
		}
	/*	Main.Convert(input);		
		
		Parser.addTabType("Guitar");
		Parser.addTitle("title");
		Parser.addTime("1");
		
		Parser parse = new Parser(input);
		Parser.addTabType("Guitar");
		Parser.addTitle("title");
		Parser.addTime("1");
		
		TextInputContentPanel.timeSig = "1";
		TextInputContentPanel.tabType = "Guitar";
		TextInputContentPanel.title = "";
		
		*/
	}

	@Test
	void testAddTitle() {
		Parser.misc = new HashMap<String, String>();
		Parser.addTitle("Test Title");
		
		assertEquals(Parser.misc.get("Title"), "Test Title");
	}

	@Test
	void testAddTabType() {
		Parser.misc = new HashMap<String, String>();
		Parser.addTabType("Guitar");
		
		assertEquals(Parser.misc.get("TabType"), "Guitar");
	}

	@Test
	void testAddTime() {
		Parser.misc = new HashMap<String, String>();
		Parser.addTime("4");
		
		assertEquals(Parser.misc.get("TimeSig"), "4");
	}

}
