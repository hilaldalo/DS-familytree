import java.util.Objects;

public class Family {

	Person oldOne;

	public Family(String name, int id) {
		oldOne = new Person(name, id);
	}

	public void addBigSon(int fatherId, String son, int sonId) {
		Person temp = searchById(this.oldOne, fatherId);
		if (temp == null) {
			Print.red("There's No Father!\n");
			return;
		}
		temp.bigSon = new Person(son, sonId);
		temp.bigSon.father = temp;
	}

	public void addBro(int personId, String bro, int broId) {
		Person temp = searchById(this.oldOne, personId);
		if (temp == null) {
			Print.red("There's No One with That Name!\n");
		}
		temp.bro = new Person(bro, broId);
		temp.bro.father = temp.father;
	}

	public void addNew(Person father, String son, int sonId, int childNumber) {
		Person temp = father;
		if (temp == null) {
			Print.red("There's No Father!\n");
			return;
		}
		if (temp.bigSon != null && childNumber > 1) {
			temp = temp.bigSon;
			for (int i = 1; i < childNumber - 1 && temp.bro != null; i++)
				temp = temp.bro;
			temp.bro = new Person(son, sonId, temp.father, temp.bro);
			return;
		}
		if (childNumber == 1) {
			if (temp.bigSon != null) {
				temp.bigSon = new Person(son, sonId, temp, temp.bigSon);
			} else {
				temp.bigSon = new Person(son, sonId, temp, null);
			}
			return;
		} else
			Print.red("Add is Not Done!!!\n");
	}

	public void delete(Person person) {

		Person temp = person;
		if (temp == null) {
			Print.red("No One Found !\n");
			return;
		}
		if (temp.father == null) {
			oldOne = null;
			Print.green("Family Been Removed!\n");
			return;
		}
		if (temp.father.bigSon.id == person.id) {
			temp.father.bigSon = temp.father.bigSon.bro;
			Print.green("Person Deleted Succecfully!!\n");
			return;
		}
		temp = temp.father.bigSon;
		while (temp.bro.id != person.id)
			temp = temp.bro;
		temp.bro = temp.bro.bro;
		Print.green("Person Deleted Succecfully!!\n");
		return;
	}

	public Person search(Person person, String name) {
		if (Objects.equals(person.name, name))
			return person;
		if (person.bro != null) {
			Person broResult = search(person.bro, name);
			if (broResult != null)
				return broResult;
		}
		if (person.bigSon != null) {
			Person bigSonResult = search(person.bigSon, name);
			if (bigSonResult != null)
				return bigSonResult;
		}
		return null;

	}

	public Person searchById(Person person, int id) {
		if (person == null) {
			return null;
		}
		if (person.id == id)
			return person;
		if (person.bro != null) {
			Person broResult = searchById(person.bro, id);
			if (broResult != null)
				return broResult;
		}
		if (person.bigSon != null) {
			Person bigSonResult = searchById(person.bigSon, id);
			if (bigSonResult != null)
				return bigSonResult;
		}
		return null;
	}

	public void printSons(Person person) {
		Person temp = person;
		if (temp == null) {
			Print.red("No One Found !\n");
			return;
		}
		if (temp.bigSon == null) {
			Print.red("There's No Son!\n");
			return;
		}
		Print.green("Sons: " + temp.bigSon.name);
		temp = temp.bigSon.bro;
		while (temp != null) {
			Print.green(" , " + temp.name);
			temp = temp.bro;
		}
		System.out.println("");
	}

	public void printBrothersAndNephews(Person person) {
		Person temp = person;
		if (temp == null) {
			Print.red("No One Found !\n");
			return;
		}
		if (temp.bro == null) {
			Print.red("There's No Brother!\n");
			return;
		}
		temp = temp.father.bigSon;
		while (temp != null) {
			if (temp.id != person.id) {
				Print.green(temp.name + " ");
				printSons(temp);
			}
			temp = temp.bro;
		}

	}

	public void printNephews(Person person) {
		Person temp = person;
		if (temp == null) {
			Print.red("No One Found!\n");
			return;
		}
		if (temp.bigSon == null) {
			Print.red("There's No Son!\n");
			return;
		}
		Print.green(temp.bigSon.name + " ");
		printSons(temp.bigSon);
		temp = temp.bigSon.bro;
		while (temp != null) {
			Print.green(temp.name + " ");
			printSons(temp);
			temp = temp.bro;
		}
	}

	public void printUncles(Person person) {
		Person temp = person;
		if (temp == null) {
			Print.red("No One Found!\n");
			return;
		}
		temp = temp.father;
		if (temp == null || temp.bro == null) {
			Print.red("Thers's No Uncle!\n");
			return;
		}
		printBrothersAndNephews(temp);
	}

	public int getNumber(Person person) {
		if (person == null)
			return 0;
		return 1 + getNumber(person.bro) + getNumber(person.bigSon);
	}

	public void print(Person p) {
		if (p == null) {
			Print.red("There's No Family!\n");
			return;
		}
		if (p.bro != null) {
			p.bro.printPerson(true, "");
		}
		if (p.name == null)
			System.out.print(" ");
		else
			System.out.print(p.name + ":" + p.id + "\n");
		if (p.bigSon != null) {
			p.bigSon.printPerson(false, "");
		}
	}
}