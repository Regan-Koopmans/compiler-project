/*

    CLASS       : Main
    DESCRIPTION : Manages the reading of files and provides an entry point to
                  the Lexer and Parser classes.

*/

import compiler.lexer.*;
import compiler.parser.*;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.lang.StringBuilder;

class Main {

  // Helper method to neaten error messages

  public static void fatalError(String message) {
    System.out.println(message);
    System.exit(1);
  }

  public static void main(String[] args) {

    if (args.length == 0) {
      fatalError("You did not pass an input file.");
    }

    // Create an instance of the Lexer class.
    Lexer lex = new Lexer();

    // Create an instance of the Parser class
    Parser parser = new Parser();

    // Create String from file.
    StringBuilder sb = new StringBuilder();
    File inputFile = new File(args[0]);

    try {
      FileReader fileReader = new FileReader(inputFile);
      BufferedReader reader = new BufferedReader(fileReader);
      String line;
      int lineNumber = 1;

      while ((line = reader.readLine()) != null) {
        lex.scan(line, lineNumber);
        lineNumber++;
      }
      fileReader.close();

    } catch (Exception e) { fatalError("There was a problem reading the file"); }

    for (Token token:lex.getTokens()) {
      System.out.println(token);
      System.out.println("");
    }

    // Invoke the parser, passing the list of tokens
    parser.parse(lex.getTokens());
  }
}
