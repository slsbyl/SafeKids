class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    // TODO: HomeScreen UI will be handled in ui-HomeScreen branch
                    SafeKidsHomeScreen()
                }
            }
        }
    }
}
