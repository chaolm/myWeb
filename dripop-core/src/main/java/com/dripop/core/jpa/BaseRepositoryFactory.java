package com.dripop.core.jpa;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.core.RepositoryMetadata;

import javax.persistence.EntityManager;
import java.io.Serializable;

import static org.springframework.data.querydsl.QueryDslUtils.QUERY_DSL_PRESENT;

public  class BaseRepositoryFactory<T, ID extends Serializable>
    extends JpaRepositoryFactory {

    private final EntityManager entityManager;

    public BaseRepositoryFactory(EntityManager em) {
      super(em);
      this.entityManager = em;
    }

    protected Object getTargetRepository(RepositoryMetadata metadata) {
    	SimpleJpaRepository<?, ?> repository = getTargetRepository(metadata, entityManager);
		return repository;
    }

    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
    	if (isQueryDslExecutor(metadata.getRepositoryInterface())) {
			return QueryDslJpaRepository.class;
		} else {
			return BaseRepositoryImpl.class;
		}
    }

	protected SimpleJpaRepository<T, ID> getTargetRepository(
			RepositoryMetadata metadata, EntityManager entityManager) {
		Class<?> repositoryInterface = metadata.getRepositoryInterface();
		@SuppressWarnings("unchecked")
		JpaEntityInformation<T, ID> entityInformation = (JpaEntityInformation<T, ID>) getEntityInformation(metadata.getDomainType());
		SimpleJpaRepository<T, ID> repo = isQueryDslExecutor(repositoryInterface) ? new QueryDslJpaRepository<T,ID>(
				entityInformation, entityManager) : new BaseRepositoryImpl<T, ID>(entityInformation, entityManager);
		return repo;
	}
	private boolean isQueryDslExecutor(Class<?> repositoryInterface) {
		return QUERY_DSL_PRESENT && QueryDslPredicateExecutor.class.isAssignableFrom(repositoryInterface);
	}
  }
