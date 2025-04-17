service Test {
  inputPort testInPort {
    Redirects: Test1 => Test1Service, Test2 => Test2Service
  }
}