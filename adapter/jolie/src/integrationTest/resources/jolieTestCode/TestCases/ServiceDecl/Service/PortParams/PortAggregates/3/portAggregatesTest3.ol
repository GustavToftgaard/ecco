service Test {
  inputPort testInPort {
    aggregates:
      testOutputPort1,
      testOutputPort2
  }
}