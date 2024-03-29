describe('web_assignment', function () {
    let page;
    let newTaskContent;

    before (async function () {
      page = await browser.newPage();
      let random = new Date().getMilliseconds();
      newTaskContent = 'new todo item ' + random;
      await page.goto('http://127.0.0.1:3000/');
    });
  
    after (async function () {
      await page.close();
    });

    it('correct title', async function() {
        expect(await page.title()).to.eql('web_assignment');
    });

    describe('add task test', function () {
        it('insert a task at the end correctly', async function() {
            await page.waitFor('.task-input');
            let originalItemsCount = await page.$$('.task-item').then(item => item.length);

            await page.click('.task-input');
            await page.type('.task-input', newTaskContent);
            await page.click('.submit-button');
            let newTask = await page.waitFor('.task-items .task-item:nth-child('+ (originalItemsCount + 1) +')');
            const expectInputContent = await page.evaluate(newTask => newTask.querySelector('textarea').textContent, newTask);
            expect(expectInputContent).to.eql(newTaskContent);
          });
    });

    describe('edit task test', function () {
        it('update task correctly', async function() {
            const updatedContent = 'updated content';
            await page.waitFor('.task-input');

            await page.click('.task-items .task-item:last-child .edit-button');
            const textareaElement=await page.$('.task-item:last-child textarea');
            await textareaElement.click( {clickCount: 3})
            await textareaElement.type(updatedContent);
            await page.$eval('.task-item:last-child textarea', textarea => textarea.blur());

            let theLastItem = await page.waitFor('.task-items .task-item:last-child');
            const expectInputContent = await page.evaluate(task => task.querySelector('textarea').textContent, theLastItem);
            expect(expectInputContent).to.eql(updatedContent);
          });
    });

    describe('delete the new task test', function () {
        it('delete last task correctly', async function() {
            await page.waitFor('.task-input');
            let originalItemsCount = await page.$$('.task-item').then(item => item.length);

            await page.click('.task-items .task-item:last-child .delete-button');
            await page.waitFor(500);

            let itemsCount = await page.$$('.task-item').then(item => item.length);
            expect(originalItemsCount - itemsCount).to.eql(1);
          });
    });

  });