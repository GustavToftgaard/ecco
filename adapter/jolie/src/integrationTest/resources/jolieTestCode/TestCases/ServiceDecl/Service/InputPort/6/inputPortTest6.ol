service Test {
  inputPort testInPort {
    location: "local"
    protocol: sodep
    interfaces: TestInterface
    aggregates: testOutputPort1, testOutputPort2
    redirects:
      Test1 => Test1Service,
      Test2 => Test2Service
  }
}