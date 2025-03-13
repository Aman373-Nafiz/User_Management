@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {
@Autowired
private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        // Setup test data
    }

    @Test
    void testCreateParentIntegration() throws Exception {
        // Test with real repository
    }
}