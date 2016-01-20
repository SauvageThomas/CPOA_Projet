package app.command;

import model.Function;

/**
 * This enum contains the differents keywords (the first word of the command)
 *
 */
public enum KeyWord {

	show {
		@Override
		public int getLen() {
			return 1;
		}

		@Override
		public void execute(Argument arg, String parameter) {
			Function.show();
		}
	},
	add {
		@Override
		public int getLenArg(Argument arg) {
			switch (arg) {
			case task:
				return 4;
			case project:
				return 3;
			default:
				return -1;
			}
		}

		@Override
		public boolean hasArgument() {
			return true;
		}

		@Override
		public void execute(Argument arg, String parameter) {
			switch (arg) {
			case task:
				Function.addTask(parameter);
			case project:
				Function.addProject(parameter);
			}

		}
	},
	remove {
		@Override
		public int getLen() {
			return 3;
		}

		public boolean hasArgument() {
			return true;
		}

		@Override
		public void execute(Argument arg, String parameter) {
			switch (arg) {
			case task:
				Function.removeTask(parameter);
			case project:
				Function.removeProject(parameter);
			}

		}
	},
	check {
		@Override
		public int getLen() {
			return 2;
		}

		@Override
		public void execute(Argument arg, String parameter) {
			Function.check(parameter);
		}
	},
	uncheck {
		@Override
		public int getLen() {
			return 2;
		}

		@Override
		public void execute(Argument arg, String parameter) {
			Function.uncheck(parameter);
		}
	},
	help {
		@Override
		public int getLen() {
			return 1;
		}

		@Override
		public void execute(Argument arg, String parameter) {
			Function.help();
		}
	},
	quit {
		@Override
		public int getLen() {
			return 1;
		}

		@Override
		public void execute(Argument arg, String parameter) {
			Function.quit();
		}
	},
	deadline {
		@Override
		public int getLen() {
			return 3;
		}

		@Override
		public void execute(Argument arg, String parameter) {
			Function.deadline(parameter);
		}
	},
	today {

		@Override
		public int getLen() {
			return 1;
		}

		@Override
		public void execute(Argument arg, String parameter) {
			Function.today();
		}
	};

	/**
	 * This method return the number of words for a given KeyWord (excepted the
	 * keyWord add)
	 * 
	 * @return length of the command
	 */
	public int getLen() {
		return -1;
	}

	/**
	 * When the command got an argument (for the add commands), this method will
	 * return you the length of the command depends on the given argument (task
	 * or project)
	 * 
	 * @param arg
	 * @return length of the command
	 */
	public int getLenArg(Argument arg) {
		return -1;
	}

	/**
	 * 
	 * This method permits to know if the given command has arguments or not.
	 */
	public boolean hasArgument() {
		return false;
	}

	public abstract void execute(Argument arg, String parameter);

}
