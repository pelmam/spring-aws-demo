package pelit.hi.logic;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @Builder @ToString
public class GreetingCard {
	private String name;
	private String remarks;
	private int luckyNumber;
}
