package app.command;

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
	},
	remove {
		@Override
		public int getLen() {
			return 3;
		}
		
		public boolean hasArgument() {
			return true;
		}
	},
	check {
		@Override
		public int getLen() {
			return 2;
		}
	},
	uncheck {
		@Override
		public int getLen() {
			return 2;
		}
	},
	help {
		@Override
		public int getLen() {
			return 1;
		}
	},
	quit {
		@Override
		public int getLen() {
			return 1;
		}
	},
	deadline {
		@Override
		public int getLen() {
			return 3;
		}
	},
	today {
		@Override
		public int getLen() {
			return 1;
		}
	};

	/**
	 * This method return the number of words for a given KeyWord (excepted the keyWord add)
	 * @return length of the command
	 */
	public int getLen() {
		return -1;
	}

	
	/**
	 * When the command got an argument (for the add commands), this method will return you the length
	 * of the command depends on the given argument (task or project)
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
}
