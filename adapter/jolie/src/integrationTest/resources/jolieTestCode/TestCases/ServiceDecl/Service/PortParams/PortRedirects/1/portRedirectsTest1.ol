service Test {
  inputPort testInPort {
    redirects: Test1 => Test1Service, Test2 => Test2Service
  }
}