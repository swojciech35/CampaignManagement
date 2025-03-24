package com.example.campaignmanagement.campaign.domain;

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
public class InMemoryCampaignRepository implements CampaignRepository {
  private Map<UUID, Campaign> table = new HashMap<>();

  @Override
  public void flush() {

  }

  @Override
  public <S extends Campaign> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public <S extends Campaign> List<S> saveAllAndFlush(Iterable<S> entities) {
    return List.of();
  }

  @Override
  public void deleteAllInBatch(Iterable<Campaign> entities) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<UUID> uuids) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Campaign getOne(UUID uuid) {
    return null;
  }

  @Override
  public Campaign getById(UUID uuid) {
    return null;
  }

  @Override
  public Campaign getReferenceById(UUID uuid) {
    return null;
  }

  @Override
  public <S extends Campaign> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Campaign> List<S> findAll(Example<S> example) {
    return List.of();
  }

  @Override
  public <S extends Campaign> List<S> findAll(Example<S> example, Sort sort) {
    return List.of();
  }

  @Override
  public <S extends Campaign> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Campaign> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Campaign> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends Campaign, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }

  @Override
  public <S extends Campaign> S save(S entity) {
    table.put(entity.getId(), entity);
    return entity;
  }

  @Override
  public <S extends Campaign> List<S> saveAll(Iterable<S> entities) {
    return List.of();
  }

  @Override
  public Optional<Campaign> findById(UUID uuid) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(UUID uuid) {
    return false;
  }

  @Override
  public List<Campaign> findAll() {
    return table.values().stream().toList();
  }

  @Override
  public List<Campaign> findAllById(Iterable<UUID> uuids) {
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
  public void delete(Campaign entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends UUID> uuids) {

  }

  @Override
  public void deleteAll(Iterable<? extends Campaign> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public List<Campaign> findAll(Sort sort) {
    return List.of();
  }

  @Override
  public Page<Campaign> findAll(Pageable pageable) {
    return null;
  }
}
