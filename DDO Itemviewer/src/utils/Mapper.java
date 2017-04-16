package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Mapper {

	private static ObjectMapper mapper;

	public static ObjectMapper getMapper() {

		if (mapper == null) {
			return new ObjectMapper();
		} else {
			return mapper;
		}
	}

}
