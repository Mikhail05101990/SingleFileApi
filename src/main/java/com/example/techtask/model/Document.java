package com.example.techtask.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Date;

@Entity
@Table(name = "documents")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Document {
  @Column(name = "id")
  @Id public BigInteger id;
  @Column(name = "doc_id")
  public String doc_id;
  @Column(name = "doc_status")
  public String doc_status;
  @Column(name = "doc_type")
  public String doc_type;
  @Column(name = "descr_id")
  public BigInteger DescriptionId;
  @Column(name = "import_request")
  public Boolean importRequest;
  @Column(name = "owner_inn")
  public String owner_inn;
  @Column(name = "participant_inn")
  public String participant_inn;
  @Column(name = "producer_inn")
  public String producer_inn;
  @Column(name = "production_type")
  public String production_type;
  @Column(name = "production_date")
  public Date production_date;
  @Column(name = "reg_date")
  public Date reg_date;
  @Column(name = "reg_number")
  public String reg_number;
}