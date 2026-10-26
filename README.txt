NIGHTFALL - complete project (Fabric, Minecraft 1.21.11)

1. Extract this zip.
2. On github.com: + > New repository > name it "nightfall" > Create.
3. Click "uploading an existing file" and drag in EVERYTHING that is inside the
   extracted folder (build.gradle, src, .github, ...). Commit.
   Check that the ".github/workflows/build.yml" file arrived (Mac hides dot-folders).
4. Open the Actions tab. The build runs automatically.
5. Green = open the run, download "nightfall-jar" under Artifacts, unzip it, and use
   the jar WITHOUT "-sources" in its name.
6. Modrinth App: Fabric 1.21.11 instance + Fabric API + this jar in the mods folder.
   Press RIGHT SHIFT in a world.
7. Red = open the failed step and send me the error text.
