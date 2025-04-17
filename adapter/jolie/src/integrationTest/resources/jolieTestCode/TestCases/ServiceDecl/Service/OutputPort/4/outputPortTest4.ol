service Test {
  outputPort testOutPort {
    location: "local"
    protocol: sodep
    interfaces: TestInterface
  }
}