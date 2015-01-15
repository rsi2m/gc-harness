import groovy.transform.Field

def dacapoBenchmarks = [/*"avrora","batik",*//*"eclipse",*//*"fop","h2","jython","luindex","lusearch","pmd","sunflow",*/"tomcat"/*,"tradebeans","tradesoap","xalan"*/]

@Field def dataFolderPath = new File("output").absolutePath
@Field def dacapoPath = this.getClass().getResource("dacapo.jar").file
@Field def gcViewerPath = this.getClass().getResource("gc-viewer.jar").file

cleanUp()
createSubFolders()

for (String benchmark : dacapoBenchmarks){
  new DacapoBenchmarkAnalyzer(dacapoPath, gcViewerPath, benchmark, dataFolderPath).analyzeBenchmark("-XX:+UseParallelOldGC")
}

def createSubFolders() {
  new File("$dataFolderPath").mkdir();
  new File("$dataFolderPath/logs").mkdir()
  new File("$dataFolderPath/data").mkdir()
}

def cleanUp() {
  new File("$dataFolderPath/logs").deleteDir()
  new File("$dataFolderPath/data").deleteDir()
}