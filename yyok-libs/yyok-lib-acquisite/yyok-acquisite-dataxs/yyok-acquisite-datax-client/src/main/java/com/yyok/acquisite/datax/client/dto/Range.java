package com.yyok.acquisite.datax.client.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Range implements Serializable {

  private String startRowkey;

  private String endRowkey;

  private Boolean isBinaryRowkey;
}
