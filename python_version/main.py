# --------V1.0
# import heapq
# from datetime import datetime, timedelta
# 
# # Задаем входные данные
# n, m = map(int, input().split())
# 
# tasks = []
# for i in range(n):
#     task = input().split(', ')
#     name = task[0].strip('"')
#     duration = int(task[1])
#     workers_needed = int(task[2])
#     dependencies = task[3].strip('[]').split(', ')
#     dependencies = [d.strip('"') for d in dependencies]
#     tasks.append({'name': name, 'duration': duration, 'workers_needed': workers_needed, 'dependencies': dependencies, 'start_time': None, 'end_time': None})
# 
# # Строим граф зависимостей
# graph = {}
# for task in tasks:
#     graph[task['name']] = set(task['dependencies'])
# 
# # Задаем список исполнителей
# workers = [datetime.min] * m
# 
# # Выполняем топологическую сортировку графа
# queue = []
# for task in tasks:
#     if not any(task['name'] in t['dependencies'] for t in tasks):
#         heapq.heappush(queue, task)
# 
# completed_tasks = []
# 
# while queue or any(worker != datetime.min for worker in workers):
#     # Выбираем задачи, которые можно выполнить, т.е. у которых выполнены все задачи-предшественники и есть доступные исполнители
#     available_tasks = []
#     for task in tasks:
#         if task not in completed_tasks and not graph[task['name']]:
#             if task['workers_needed'] <= len([w for w in workers if w <= datetime.now()]):
#                 heapq.heappush(available_tasks, task)
# 
#     # Запускаем выполнение задач
#     while available_tasks and any(worker == datetime.min for worker in workers):
#         task = heapq.heappop(available_tasks)
#         start_time = max([t['end_time'] for t in tasks if t['name'] in graph[task['name']]] + [datetime.now()])
#         workers_count = task['workers_needed']
#         free_workers = [i for i, worker in enumerate(workers) if worker <= start_time]
#         if len(free_workers) >= workers_count:
#             for i in range(workers_count):
#                 workers[free_workers[i]] = start_time + timedelta(minutes=task['duration'])
#             task['start_time'] = start_time
#             task['end_time'] = max(workers)
#             completed_tasks.append(task)
#         else:
#             heapq.heappush(available_tasks, task)
# 
#     # Обновляем время завершения задач и освобождаем исполнителей, занятых выполнением этих задач
#     for task in completed_tasks:
#         if task['end_time'] <= datetime.now():
#             for i in range(task['workers_needed']):
#                 workers[workers.index(task['end_time'])] = datetime.min
#                 tasks.remove(task)
# 
#     # Обновляем граф зависимостей
#     for task in completed_tasks:
#         for t in tasks:
#             if task['name'] in t['dependencies']:
#                 graph[t['name']].remove(task['name'])
#                 if not graph[t['name']] and t not in available_tasks:
#                     heapq.heappush(queue, t)
# 
# # Выводим результат
# for task in completed_tasks:
#     print(task['name'], task['start_time'].strftime('%H:%M:%S'), task['end_time'].strftime('%H:%M:%S'))




# -----V2.0

# import heapq
# 
# # Задаем входные данные
# n, m = map(int, input().split())
# 
# tasks = []
# for i in range(n):
#     task = input().split(', ')
#     name = task[0].strip('"')
#     duration = int(task[1])
#     dependencies = task[2].strip('[]').split(', ')
#     dependencies = [d.strip('"') for d in dependencies]
#     tasks.append((name, duration, dependencies, None, None))
# 
# # Строим граф зависимостей
# graph = {}
# for task in tasks:
#     graph[task[0]] = set(task[2])
# 
# # Выполняем топологическую сортировку графа
# queue = []
# for task in tasks:
#     if not any(task[0] in t[2] for t in tasks):
#         heapq.heappush(queue, task)
# 
# while queue:
#     task = heapq.heappop(queue)
#     start_time = max(t[4] + t[1] for t in tasks if t[0] in graph[task[0]])
#     task = list(task)
#     task[3] = start_time
#     task[4] = start_time + task[1]
#     task = tuple(task)
#     tasks.sort(key=lambda t: t[4] or float('inf'))
#     for t in tasks:
#         if task[0] in t[2]:
#             graph[t[0]].remove(task[0])
#             if not graph[t[0]]:
#                 heapq.heappush(queue, t)
# 
# # Выводим результат
# for task in tasks:
#     print(task[0], task[3], task[4])





# -----V3.0---------------------------------
class Task:
    name: str
    duration: int
    workers_needed: int
    dependencies = []
    def __int__(self, name: str, duration: int, workers_needed: int):
        self.name = name
        self.duration = duration
        self.workers_needed = workers_needed


import heapq


# 1)Задаем входные данные
print("Введите два числа через пробел: N = количество задач и M = количество исполнителей")
n, m = map(int, input().split())

print("Вводите " + n + 'задач, по задаче на строку в формате: \n "Текст задачи", время выполнения в единицах, '
                       'необходимое количество исполнителей, ["Название задачи-предшественника №1", ...] или []')
print("Примечание: задачи-предшественники должны вводится строго ДО введения задачи, которой они предшествуют")

tasks = []
for i in range(n):
    taskText = input().split(', ')
    name = taskText[0].strip('"')
    duration = int(taskText[1])
    workers_needed = int(taskText[2])

    dependeciesStrList = [d.strip('"') for d in taskText[3].strip('[]').split(', ')] 
    dependeciesTaskList = []
    
    for elem in dependeciesStrList:
        if !tasks.__contains__()
    
    newTask = Task(name, duration, workers_needed)
    tasks.append(newTask)
    
   
  

            

# 2)Строим граф зависимостей
graph = {}
for task in tasks:
    graph[task['name']] = set(task['dependencies'])

# 3)Задаем список исполнителей
workers = [1] * m

# 4)Выполняем топологическую сортировку графа
queue = []
for task in tasks:
    if not any(task['name'] in t['dependencies'] for t in tasks):
        heapq.heappush(queue, task)

completed_tasks = []

while queue or any(worker != datetime.min for worker in workers):
    # Выбираем задачи, которые можно выполнить, т.е. у которых выполнены все задачи-предшественники и есть доступные исполнители
    available_tasks = []
    for task in tasks:
        if task not in completed_tasks and not graph[task['name']]:
            if task['workers_needed'] <= len([w for w in workers if w <= datetime.now()]):
                heapq.heappush(available_tasks, task)

    # Запускаем выполнение задач
    while available_tasks and any(worker == datetime.min for worker in workers):
        task = heapq.heappop(available_tasks)
        if all(t['start_time'] is not None for t in tasks if task['name'] in t['dependencies']):
            start_time = max([t['end_time'] for t in tasks if t['name'] in graph[task['name']]] + [datetime.now()])
        else:
            start_time = datetime.now()
        workers_count = task['workers_needed']
        free_workers = [i for i, worker in enumerate(workers) if worker <= start_time]
        if len(free_workers) >= workers_count:
            for i in range(workers_count):
                workers[free_workers[i]] = start_time + timedelta(minutes=task['duration'])
            task['start_time'] = start_time
            task['end_time'] = max(workers)
            completed_tasks.append(task)
        else:
            heapq.heappush(available_tasks, task)

    # Обновляем время завершения задач и освобождаем исполнителей, занятых выполнением этих задач
    for task in completed_tasks:
        if task['end_time'] <= datetime.now():
            for i in range(task['workers_needed']):
                workers[workers.index(task['end_time'])] = datetime.min
            tasks.remove(task)

    # Обновляем граф зависимостей
    for task in completed_tasks:
        for t in tasks:
            if task['name'] in t['dependencies']:
                graph[t['name']].remove(task['name'])
                if not graph[t['name']] and t not in available_tasks:
                    heapq.heappush(queue, t)

# 5)Выводим результат
for task in completed_tasks:
    print(task['name'], task['start_time'].strftime('%H:%M:%S'), task['end_time'].strftime('%H:%M:%S'))