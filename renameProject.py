import os, re

oldProjectName = 'Template'

''' Replace NewProjectName '''
newProjectName = "superPX"

projectRoot = './src'
packageRoot = projectRoot + '/main/java/com/tmax/edu/'
resourceRoot = projectRoot + '/main/resources/'
testPackageRoot = projectRoot + '/test/java/com/tmax/edu/'

oldApplicationName = oldProjectName + '/Application'
newApplicationName = newProjectName + '/Application'

oldApplicationTestName = oldProjectName + '/ApplicationTests'
newApplicationTestName = newProjectName + '/ApplicationTests'

oldPackageDir = packageRoot + oldProjectName.lower()
newPackageDir = packageRoot + newProjectName.lower()

oldTestPackageDir = testPackageRoot + oldProjectName.lower()
newTestPackageDir = testPackageRoot + newProjectName.lower()

if os.path.isdir(oldPackageDir):
    os.rename(oldPackageDir, newPackageDir)
if os.path.isdir(oldTestPackageDir):
    os.rename(oldTestPackageDir, newTestPackageDir)

oldPackageName = 'com.tmax.edu.' + oldProjectName.lower()
newPackageName = 'com.tmax.cloud.' + newProjectName.lower()

for path, dirs, files in os.walk(projectRoot):
    for filename in files:
        filepath = os.path.join(path, filename)
        with open(filepath, 'r', encoding='utf-8') as f:
            s = f.read()
        if s.find(oldPackageName) != -1:
            s = s.replace(oldPackageName, newPackageName)
            with open(filepath, 'w') as f:
                f.write(s)

        if filename == oldApplicationName + '.java':
            with open(filepath, 'r', encoding='utf-8') as f:
                    s = f.read()
            if s.find(oldApplicationName) != -1:
                s = s.replace(oldApplicationName, newApplicationName)
                with open(filepath, 'w') as f:
                    f.write(s)
            os.rename(filepath, os.path.join(path, newApplicationName) + '.java')

        if filename == oldApplicationTestName + '.java':
            with open(filepath, 'r', encoding='utf-8') as f:
                    s = f.read()
            if s.find(oldApplicationTestName) != -1:
                s = s.replace(oldApplicationTestName, newApplicationTestName)
                with open(filepath, 'w') as f:
                    f.write(s)
            os.rename(filepath, os.path.join(path, newApplicationTestName) + '.java')

gradleSettingFilePath = './settings.gradle'

with open(gradleSettingFilePath, 'r', encoding='utf-8') as file:
    lines = file.readlines()

newlines = []
for line in lines:
    if line.find('rootProject.name') > -1 :
        line = """rootProject.name = '""" + newProjectName.lower() + """'"""
    newlines.append(line)

with open(gradleSettingFilePath, 'w', encoding='utf-8') as file:
    file.writelines(newlines)

applicationYmlPath = resourceRoot + 'application.yml'

with open(applicationYmlPath) as f:
    s = f.read()
if s.find(oldProjectName.lower()) != -1:
    s = s.replace(oldProjectName.lower(), newProjectName.lower())
    with open(applicationYmlPath, 'w') as f:
        f.write(s)
