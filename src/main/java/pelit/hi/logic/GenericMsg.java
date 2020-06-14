package pelit.hi.logic;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @Builder @ToString
public class GenericMsg {
	private String message;
	private Object details;
}
