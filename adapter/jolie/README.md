
# ECCO Jolie Adapter

## Adapter Navigation Guide

- Main adapter
  - **src/main/java/at.jku.isse.ecco.adapter.jolie** — Contains adapter implementation
  - **src/main/jolieGrammar** — Contains the 'homemade' Jolie grammar
- Testing
  - **src/integrationTest/java** — Contains all tests and test cases
  - **src/integrationTest/resources/JolieTestCode** — Contains all Jolie source code used in reader and writer tests

### Adapter Structure
* ``src``
    * ``main``
        * ``java``
            * ``at.jku.isse.ecco.adapter.jolie``
                * ``JolieReader.java``
                * ``JolieWriter.java``
                * ``JolieModule.java``
                * ``JoliePlugin.java``
                * ``ECCOToString.java``
                * ``highLevelParser``
                  * ``ast``
                    * ``interfaces``
                      * ``Node.java``
                      * ``NodeVisitor.java``
                    * ``nodes``
                      * ``...``
                    * ``visitors``
                      * ``Data``
                        * ``JolieContextArtifactData.java``
                        * ``JolieTokenArtifactData.java``
                        * ``JolieLineArtifactData.java``
                      * ``AstToECCO.java``
                  * ``parser``
                    * ``JolieParser.java``
                  * ``scanner``
                    * ``token``
                      * ``JolieToken.java``
                      * ``JolieTokenType.java``
                    * ``JolieScanner.java``
        * ``jolieGrammar``
          * ``grammar.txt``
        * ``resources``
            * ``META-INF.services``
                * ``at.jku.isse.ecco.adapter.ArtifactPlugin``
    * ``integrationTest``
      * ``java``
        * ``jolieReaderIntegrationTests``
          * ``interfacesAndAbstractClasses``
            * ``JolieReaderIntegrationTestCase.java``
          * ``SimpleFiles``
            * ``...``
          * ``TestCases``
            * ``...``
        * ``JolieEccoServiceTest.java``
        * ``JolieReaderIntegrationTest.java``
        * ``JolieWriterIntegrationTest.java``
      * ``resources``
        * ``JolieTestCode``
          * ``SimpleFiles``
            * ``...``
          * ``TestCases``
            * ``...``
        * ``versionTestFolders``
          * ``V1_numbers``
            * ``.config``
            * ``numbers.ol``
          * ``V2_numbers``
            * ``.config``
            * ``numbers.ol``
          * ``V3_numbers``
            * ``.config``
            * ``numbers.ol``
    * ``build.gradle`` 


