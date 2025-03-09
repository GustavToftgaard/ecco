package at.jku.isse.ecco.adapter.text.test;

import at.jku.isse.ecco.adapter.text.*;
import at.jku.isse.ecco.storage.mem.dao.*;
import at.jku.isse.ecco.tree.*;
import org.junit.jupiter.api.*;

import java.net.*;
import java.nio.file.*;
import java.util.*;

public class AdapterTest {

	private static final Path DATA_DIR;

	static {
		Path dataPath = null;
		try {
			dataPath = Paths.get(AdapterTest.class.getClassLoader().getResource("data").toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		DATA_DIR = dataPath;
	}

	private static final Path BASE_DIR = DATA_DIR.resolve("input");
	private static final Path[] FILES = new Path[]{Paths.get("file.txt")};
	private static final Path[] JOLIE_FILES = new Path[]{Paths.get("contactsBook.ol")};

	@Test
	public void Java_Adapter_Test() {
		System.out.println("Java_Adapter_Test");
		TextReader reader = new TextReader(new MemEntityFactory());

		System.out.println("READ");
		Set<Node.Op> nodes = reader.read(BASE_DIR, FILES);

		System.out.println(nodes);
	}

	@Test
	public void Jolie_Adapter_Test() {
		System.out.println("\nJolie_Adapter_Test");
		TextReader reader = new TextReader(new MemEntityFactory());

		System.out.println("READ");

		Set<Node.Op> nodes = reader.read(BASE_DIR, JOLIE_FILES);

//		 print all lines of test file
//		var iter = nodes.iterator();
//		var lines = iter.next().getChildren();
//        for (Node.Op line : lines) {
//            System.out.println(line);
//        }

		System.out.println(nodes);
	}

}
