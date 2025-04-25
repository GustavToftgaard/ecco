service Test {
  inputPort testInPort {
    Protocol: http {
      .method = "post"
    }
  }
}