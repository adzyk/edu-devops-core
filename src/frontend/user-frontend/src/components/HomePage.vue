<script setup lang="ts">
import WelcomeItem from './WelcomeItem.vue'
import DocumentationIcon from './icons/IconDocumentation.vue'
import ToolingIcon from './icons/IconTooling.vue'
import EcosystemIcon from './icons/IconEcosystem.vue'
import CommunityIcon from './icons/IconCommunity.vue'
import SupportIcon from './icons/IconSupport.vue'
</script>

<template>
  <WelcomeItem>
    <template #icon>
      <DocumentationIcon />
    </template>
    <template #heading>Цель проекта</template>

    Освоение DevOps-практик команды разработки программного обеспечения.
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <ToolingIcon />
    </template>
    <template #heading>Преимущества</template>

    Каждый из команды может обновить или вывести новый компонент в тестовый контур
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <EcosystemIcon />
    </template>
    <template #heading>Общий стандарт в команде</template>

    Набор тем для погружения в общие платформы, такие как
    <a href="https://www.docker.com/">Docker</a> и <a href="https://kubernetes.io/">Kubernetes</a>,
    и общие инструменты -
    <a href="https://about.gitlab.com/">GitLab</a>,
    <a href="https://argo-cd.readthedocs.io/en/stable/">ArgoCD</a>,
    <a href="https://www.vaultproject.io/">Vault</a>.
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <CommunityIcon />
    </template>
    <template #heading>Обмен опытом</template>

    Каждый из команды может внести свой вклад в общие подходы, улучшить существующие, внедрить новые инструменты.
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <SupportIcon />
    </template>
    <template #heading>Без ограничений</template>

    Каждый желающий может расширить систему для изучения нужных тем и инструментов.
  </WelcomeItem>
</template>
