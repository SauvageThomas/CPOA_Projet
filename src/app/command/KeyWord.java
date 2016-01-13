package app.command;

public enum KeyWord {

	show {
		public int getLen() {
			return 1;
		}
	},
	add {
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
		
		public boolean hasArgument() {
			return true;
		}
	},
	remove {
		public int getLen() {
			return 3;
		}
		
		public boolean hasArgument() {
			return true;
		}
	},
	check {
		public int getLen() {
			return 2;
		}
	},
	uncheck {
		public int getLen() {
			return 2;
		}
	},
	help {
		public int getLen() {
			return 1;
		}
	},
	quit {
		public int getLen() {
			return 1;
		}
	},
	deadline {
		public int getLen() {
			return 3;
		}
	},
	today {
		public int getLen() {
			return 1;
		}
	};

	public int getLen() {
		return -1;
	}

	public int getLenArg(Argument arg) {
		return -1;
	}
	public boolean hasArgument() {
		return false;
	}
}
