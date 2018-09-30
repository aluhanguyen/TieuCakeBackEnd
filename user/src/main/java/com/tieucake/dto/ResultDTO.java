package com.tieucake.dto;

import java.util.List;

public class ResultDTO<K> {

	public String cursor;
	public List<K> result;

	public ResultDTO(List<K> result, String cursor) {
		this.result = result;
		this.cursor = cursor;
	}

	public ResultDTO(List<K> result) {
		this.result = result;
		this.cursor = null;
	}
}
