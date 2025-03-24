package com.example.campaignmanagement.seller.domain;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.function.Function;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class InMemorySellerRepository implements SellerRepository {
  private Map<UUID, Seller> table = new HashMap<>();

  @Override
  public boolean existsSellerByName(String name) {
    return table.values().stream().anyMatch(seller -> seller.getName().equals(name));
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends Seller> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public <S extends Seller> List<S> saveAllAndFlush(Iterable<S> entities) {
    return List.of();
  }

  @Override
  public void deleteAllInBatch(Iterable<Seller> entities) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<UUID> uuids) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Seller getOne(UUID uuid) {
    return null;
  }

  @Override
  public Seller getById(UUID uuid) {
    return null;
  }

  @Override
  public Seller getReferenceById(UUID uuid) {
    return null;
  }

  @Override
  public <S extends Seller> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Seller> List<S> findAll(Example<S> example) {
    return List.of();
  }

  @Override
  public <S extends Seller> List<S> findAll(Example<S> example, Sort sort) {
    return List.of();
  }

  @Override
  public <S extends Seller> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Seller> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Seller> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends Seller, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }

  @Override
  public <S extends Seller> S save(S entity) {
    table.put(entity.getId(), entity);
    return entity;
  }

  @Override
  public <S extends Seller> List<S> saveAll(Iterable<S> entities) {
    return List.of();
  }

  @Override
  public Optional<Seller> findById(UUID uuid) {
    return table.values().stream().filter(seller -> seller.getId().equals(uuid)).findFirst();
  }

  @Override
  public boolean existsById(UUID uuid) {
    return false;
  }

  @Override
  public List<Seller> findAll() {
    return table.values().stream().toList();
  }

  @Override
  public List<Seller> findAllById(Iterable<UUID> uuids) {
    return List.of();
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(UUID uuid) {

  }

  @Override
  public void delete(Seller entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends UUID> uuids) {

  }

  @Override
  public void deleteAll(Iterable<? extends Seller> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public List<Seller> findAll(Sort sort) {
    return List.of();
  }

  @Override
  public Page<Seller> findAll(Pageable pageable) {
    return null;
  }
}
