package com.example.demo.service;

import com.example.demo.model.ChildModel;
import com.example.demo.model.ParentModel;
import com.example.demo.repository.ParentRepository;
import com.example.demo.repository.ChildRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private ParentRepository parentRepository;

    @Mock
    private ChildRepository childRepository;

    @InjectMocks
    private UserService userService;

    private ParentModel mockParent;

    @BeforeEach
    void setUp() {
        // No need to call MockitoAnnotations.openMocks(this) when using @ExtendWith(MockitoExtension.class)

        mockParent = new ParentModel();
        mockParent.setId(1L);
        mockParent.setFirstName("John");
        mockParent.setLastName("Doe");
    }

    @Test
    public void testCreateParent() {
        when(parentRepository.save(any(ParentModel.class))).thenReturn(mockParent);

        ParentModel savedParent = userService.createParent(mockParent);

        assertNotNull(savedParent);
        assertEquals("John", savedParent.getFirstName());
    }

    @Test
    public void testCreateChild() {
        ChildModel child = new ChildModel();
        child.setFirstName("Alice");
        child.setLastName("Doe");

        when(parentRepository.findById(1L)).thenReturn(Optional.of(mockParent));
        when(childRepository.save(any(ChildModel.class))).thenReturn(child);

        ChildModel savedChild = userService.createChild(1L, child);

        assertNotNull(savedChild);
        assertEquals("Alice", savedChild.getFirstName());
        assertEquals(mockParent, savedChild.getParent());
    }
}