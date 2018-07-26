import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {

		Family family = createFamily();

		// create list of ids to search between them
		LinkedList<Integer> idsList = new LinkedList<Integer>();
		createList(idsList);

		createMenu();

		Scanner input = new Scanner(System.in);
		int a, fatherId, sonId, number;
		String sonName;
		Person father, son;

		// main menu
		while ((a = input.nextInt()) != -1) {
			switch (a) {

			case 1:
				/////////////////////// Add New Person ///////////////////////////

				Print.purple("---- Add New Person ----\n");
				System.out.print("Input Father Id: ");

				fatherId = input.nextInt();
				// search for his father
				father = family.searchById(family.oldOne, fatherId);

				if (father == null) {
					Print.red("Father Not Found!!!\n");
					break;
				}
				Print.green(father.name + "\n");
				System.out.print("Input Son Id: ");

				sonId = input.nextInt();
				// search to see if id is already exist or not
				if (searchInIds(sonId, idsList)) {
					Print.red("This Id Already Exist!!!\n");
					break;
				}
				// add his id to our ids list
				idsList.add(sonId);
				// add him to family
				System.out.print("Input Son Name: ");
				sonName = input.next();

				System.out.print("Where Did He Fit In His Family: ");
				number = input.nextInt();

				family.addNew(father, sonName, sonId, number);
				// print his father sons after adding
				family.printSons(father);
				break;

			case 2:
				//////////////////////// Delete a Person //////////////////////////

				Print.purple("---- Delete A Person ----\n");
				System.out.print("Input Person Id: ");

				sonId = input.nextInt();
				// find him in family
				son = family.searchById(family.oldOne, sonId);
				if (son == null) {
					Print.red("Person not Found!\n");
					break;
				}
				String b;
				Print.green("Are You Sure You Want To Delete ");
				Print.red(son.name);
				Print.green(" ? y or n\n");
				b = input.next();

				if (b.equals("y")) {
					// delete his id from our ids list
					idsList.remove(new Integer(sonId));
					// delete him from family
					family.delete(son);
				}
				break;

			case 3:
				//////////////////////////// Print a Person Sons /////////////////////////

				Print.purple("---- Print A Person Sons ----\n");
				System.out.print("Input A Person Id: ");

				fatherId = input.nextInt();
				family.printSons(family.searchById(family.oldOne, fatherId));
				break;

			case 4:
				///////////////////////////////// Print a Person Brother /////////////////////

				Print.purple("---- Print A Person Brothers And Nephews  ----\n");
				System.out.print("Input A Person Id: ");

				sonId = input.nextInt();
				family.printBrothersAndNephews(family.searchById(family.oldOne, sonId));
				break;

			case 5:
				/////////////////////////////// Print a Person Nephews //////////////////////

				Print.purple("---- Print A Person Nephews  ----\n");
				System.out.print("Input Person Id: ");

				fatherId = input.nextInt();
				family.printNephews(family.searchById(family.oldOne, fatherId));

				break;

			case 6:
				//////////////////////////// Print Number //////////////////////////////

				Print.purple("---- Print Numbers  ----\n");
				int num = family.getNumber(family.oldOne);
				Print.green("Numbers Are: " + num + "\n");
				break;
			case 7:
				//////////////////////////////// Print a Person Uncles //////////////////

				Print.purple("---- Print A Person Uncles  ----\n");
				System.out.print("Input A Person Id: ");

				sonId = input.nextInt();
				family.printUncles(family.searchById(family.oldOne, sonId));
				break;

			case 8:
				/////////////////////////////// Print family ////////////////////////////

				family.print(family.oldOne);
				break;

			case 9:
				///////////////////////////// save family //////////////////////////////

				createIdsFile(family);
				createFamilyFile(family);
				Print.green("---- Family Saved Succesfuly!!!  ----\n");
				break;

			case 10:
				return;
			}
			createMenu();
		}
		input.close();
	}

	public static void createIdsFile(Family f) {

		String fileName = "familyId.txt";
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			addToIdsFile(f.oldOne, bufferedWriter);
			bufferedWriter.close();

		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");

		}

	}

	public static void addToIdsFile(Person person, BufferedWriter bufferedWriter) {

		String fileName = "familyId.txt";
		try {
			bufferedWriter.write(person.name + "\t" + person.id);
			bufferedWriter.newLine();
			if (person.bro != null)
				addToIdsFile(person.bro, bufferedWriter);
			if (person.bigSon != null)
				addToIdsFile(person.bigSon, bufferedWriter);
		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");

		}
	}

	public static void createFamilyFile(Family f) {
		String fileName = "family.txt";
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(f.oldOne.name + " " + f.oldOne.id);
			bufferedWriter.newLine();
			addToFamilyFile(f.oldOne, bufferedWriter);
			bufferedWriter.close();

		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");

		}
	}

	public static void addToFamilyFile(Person person, BufferedWriter bufferedWriter) {
		String fileName = "family.txt";

		try {
			if (person.bigSon != null) {
				Person temp = person.bigSon;
				bufferedWriter.write(person.id + " ");
				while (temp != null) {
					bufferedWriter.write(temp.name + " " + temp.id + " ");
					temp = temp.bro;
				}
				bufferedWriter.newLine();
				temp = person.bigSon;
				while (temp != null) {
					addToFamilyFile(temp, bufferedWriter);
					temp = temp.bro;
				}
			}

		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");

		}
	}

	public static boolean searchInIds(int id, LinkedList<Integer> l) {

		for (int i = 0; i < l.size(); i++)
			if (id == l.get(i))
				return true;

		return false;
	}

	public static void createList(LinkedList<Integer> l) {
		String fileName = "familyId.txt";

		try {
			Scanner fileReader = new Scanner(new File(fileName));
			String line;
			while (fileReader.hasNextLine()) {
				line = fileReader.nextLine();
				String[] words = line.split("\t");
				l.add(Integer.valueOf(words[1]));
			}
			fileReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		}
	}

	public static Family createFamily() {
		String name;
		int fatherId, personId, broId;
		String fileName = "family.txt";
		try {
			Scanner fileReader = new Scanner(new File(fileName));
			Scanner lineReader = new Scanner(fileReader.nextLine());

			name = lineReader.next();
			fatherId = lineReader.nextInt();
			Family family = new Family(name, fatherId);

			while (fileReader.hasNextLine()) {
				lineReader = new Scanner(fileReader.nextLine());

				fatherId = lineReader.nextInt();
				name = lineReader.next();
				personId = lineReader.nextInt();
				family.addBigSon(fatherId, name, personId);

				while (lineReader.hasNext()) {
					name = lineReader.next();
					broId = lineReader.nextInt();
					family.addBro(personId, name, broId);
					personId = broId;
				}

			}
			lineReader.close();
			fileReader.close();
			return family;
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		}
		return null;
	}

	public static void createMenu() {

		Print.yellow("1.Add New Person\n" + "2.Delete A Person\n" + "3.Print A Person Sons\n"
				+ "4.Print A Person Brothers And Nephews\n" + "5.Print A Person Nephews\n" + "6.Print Number\n"
				+ "7.Print A Person Uncles\n" + "8.Print All Family\n" + "9.Save Family\n" + "10.Exit\n");

	}
}