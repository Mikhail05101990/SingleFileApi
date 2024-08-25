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
  @Id public BigInteger Id;
  @Column(name = "doc_id")
  public String DocumentId;
  @Column(name = "doc_status")
  public String Status;
  @Column(name = "doc_type")
  public String DocumentType;
  @Column(name = "descr_id")
  public BigInteger DescriptionId;
  @Column(name = "import_request")
  public Boolean ImportRequest;
  @Column(name = "owner_inn")
  public String OwnerInn;
  @Column(name = "participant_inn")
  public String ParticipantInn;
  @Column(name = "producer_inn")
  public String ProducerInn;
  @Column(name = "production_type")
  public String ProductionType;
  @Column(name = "production_date")
  public Date ProductionDate;
  @Column(name = "reg_date")
  public Date RegistrationDate;
  @Column(name = "reg_number")
  public String RegistrationNumber;
}