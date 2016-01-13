package app.command;

public class CommandFactory {

	public Command getCommandFromString(String commandLine) {

		String[] args = commandLine.split(" ", 2);

		KeyWord keyWord;
		try {
			keyWord = KeyWord.valueOf(args[0]);
		} catch (java.lang.IllegalArgumentException e) {
			return null;
		}

		// Si mots clé unique (help, show)
		if (args.length == 1) {
			// System.out.println("help/show/quit/today");
			if (keyWord.equals(KeyWord.help) || keyWord.equals(KeyWord.show)
					|| keyWord.equals(KeyWord.quit)
					|| keyWord.equals(KeyWord.today)) {
				return new Command(keyWord);
			}
			return null;
		}

		String[] tmp = args[1].split(" ", 2);

		Argument arg;
		try {
			arg = Argument.valueOf(tmp[0]);
		} catch (java.lang.IllegalArgumentException e) {

			try {
				Argument.valueOf(args[0]);
			} catch (java.lang.IllegalArgumentException r) {

				// Si mot clé + string (check <task>, uncheck <task>)

				if (tmp.length == 1) {
					return new Command(keyWord, tmp[0]);
				} else {
					return new Command(keyWord, tmp[0] + " " + tmp[1]);
				}
			}

			return null;
		}

		if (tmp.length == 1) {
			return null;
		}

		// (add, remove)

		// System.out.println(tmp[1]);
		// System.out.println("add");
		return new Command(keyWord, arg, tmp[1]);
	}
}
