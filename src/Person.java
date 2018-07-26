
public class Person {

	String name;
	Person bro;
	Person bigSon;
	Person father;
	int id;

	public Person() {
		this.name = null;
		this.bro = this.bigSon = this.father = null;
		this.id = 0;
	}

	public Person(String n, int id) {
		this.name = n;
		this.bro = this.bigSon = this.father = null;
		this.id = id;
	}

	public Person(String n, int id, Person father, Person bro) {
		this.name = n;
		this.bro = bro;
		this.bigSon = null;
		this.father = father;
		this.id = id;
	}

	public void printPerson(boolean isBro, String indent) {
		if (this.bro != null) {
			this.bro.printPerson(true, indent + (isBro ? "             " : " |             "));
		}
		System.out.print(indent);
		if (isBro) {
			System.out.print("Bro: ");
		} else {
			System.out.print("BigSon: ");
		}
		System.out.print("----- ");
		System.out.print(ANSI_GREEN + this.name + ANSI_RESET + ":" + ANSI_YELLOW + this.id + ANSI_RESET + "\n");
		if (this.bigSon != null) {
			this.bigSon.printPerson(false, indent + (isBro ? " |           " : "              "));
		}
	}

}