package com.pragma.reto_aws.infrastructure.out.postgres.adapter;

import com.pragma.reto_aws.dominio.model.User;
import com.pragma.reto_aws.infrastructure.out.postgres.entity.UserEntity;
import com.pragma.reto_aws.infrastructure.out.postgres.mapper.UserEntityMapper;
import com.pragma.reto_aws.infrastructure.out.postgres.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserPersistenceAdapterTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private UserEntityMapper userEntityMapper;

    private UserPersistenceAdapter userPersistenceAdapter;

    @BeforeEach
    void setUp() {
        userPersistenceAdapter = new UserPersistenceAdapter(userRepository, userEntityMapper);
    }

    @Test
    void shouldReturnTrueWhenEmailExists() {
        when(userRepository.existsByEmail("user@mail.com")).thenReturn(Mono.just(true));

        StepVerifier.create(userPersistenceAdapter.existsByEmail("user@mail.com"))
                .expectNext(true)
                .verifyComplete();

        verify(userRepository).existsByEmail("user@mail.com");
    }

    @Test
    void shouldReturnFalseWhenEmailDoesNotExist() {
        when(userRepository.existsByEmail("user@mail.com")).thenReturn(Mono.just(false));

        StepVerifier.create(userPersistenceAdapter.existsByEmail("user@mail.com"))
                .expectNext(false)
                .verifyComplete();

        verify(userRepository).existsByEmail("user@mail.com");
    }

    @Test
    void shouldReturnTrueWhenIdentificationNumberExists() {
        when(userRepository.existsByIdentificationNumber("123456")).thenReturn(Mono.just(true));

        StepVerifier.create(userPersistenceAdapter.existsByIdentificationNumber("123456"))
                .expectNext(true)
                .verifyComplete();

        verify(userRepository).existsByIdentificationNumber("123456");
    }

    @Test
    void shouldReturnFalseWhenIdentificationNumberDoesNotExist() {
        when(userRepository.existsByIdentificationNumber("123456")).thenReturn(Mono.just(false));

        StepVerifier.create(userPersistenceAdapter.existsByIdentificationNumber("123456"))
                .expectNext(false)
                .verifyComplete();

        verify(userRepository).existsByIdentificationNumber("123456");
    }

    @Test
    void shouldSaveUserSuccessfully() {
        User user = User.builder()
                .identificationNumber("123456")
                .fullName("Juan Perez")
                .email("juan@mail.com")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .identificationNumber("123456")
                .fullName("Juan Perez")
                .email("juan@mail.com")
                .build();

        UserEntity savedEntity = UserEntity.builder()
                .id(1L)
                .identificationNumber("123456")
                .fullName("Juan Perez")
                .email("juan@mail.com")
                .build();

        User savedUser = User.builder()
                .id(1L)
                .identificationNumber("123456")
                .fullName("Juan Perez")
                .email("juan@mail.com")
                .build();

        when(userEntityMapper.toEntity(user)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(Mono.just(savedEntity));
        when(userEntityMapper.toDomain(savedEntity)).thenReturn(savedUser);

        StepVerifier.create(userPersistenceAdapter.save(user))
                .expectNext(savedUser)
                .verifyComplete();

        verify(userEntityMapper).toEntity(user);
        verify(userRepository).save(userEntity);
        verify(userEntityMapper).toDomain(savedEntity);
    }

    @Test
    void shouldFindUserByIdSuccessfully() {
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .identificationNumber("123456")
                .fullName("Juan Perez")
                .email("juan@mail.com")
                .build();

        User user = User.builder()
                .id(1L)
                .identificationNumber("123456")
                .fullName("Juan Perez")
                .email("juan@mail.com")
                .build();

        when(userRepository.findById(1L)).thenReturn(Mono.just(userEntity));
        when(userEntityMapper.toDomain(userEntity)).thenReturn(user);

        StepVerifier.create(userPersistenceAdapter.findById(1L))
                .expectNext(user)
                .verifyComplete();

        verify(userRepository).findById(1L);
        verify(userEntityMapper).toDomain(userEntity);
    }

    @Test
    void shouldReturnEmptyWhenUserByIdDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(userPersistenceAdapter.findById(1L))
                .verifyComplete();

        verify(userRepository).findById(1L);
        verify(userEntityMapper, never()).toDomain(any());
    }
}
